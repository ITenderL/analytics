// package com.itender.analytics.tag.service.impl;
//
//
// import cn.hutool.core.util.ObjectUtil;
// import cn.hutool.core.util.StrUtil;
// import com.alibaba.fastjson.JSON;
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.alibaba.fastjson.serializer.SerializerFeature;
// import com.itender.analytics.tag.domain.TagGroupExpressionRequest;
// import com.itender.analytics.tag.domain.TagGroupSelect;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.commons.lang3.exception.ExceptionUtils;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.BeanUtils;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.util.CastUtils;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.transaction.support.TransactionSynchronizationAdapter;
// import org.springframework.transaction.support.TransactionSynchronizationManager;
// import org.springframework.util.CollectionUtils;
//
// import javax.annotation.Resource;
// import java.math.BigDecimal;
// import java.math.RoundingMode;
// import java.util.*;
// import java.util.concurrent.*;
// import java.util.stream.Collectors;
//
// import static com.itender.analytics.tag.enums.ExpressionTypeEnum.TAG_GROUP;
//
//
// @Slf4j
// @Service
// public class TagServiceImpl implements TagService {
//
//     @Resource
//     private AttributeConfCustomMapper attributeConfCustomMapper;
//
//     @Resource
//     private TagCustomMapper tagCustomMapper;
//
//     @Resource
//     private TagGroupCustomMapper tagGroupCustomMapper;
//
//     @Resource
//     private TagGroupRelationCustomMapper tagGroupRelationCustomMapper;
//
//     @Resource
//     private TemplateFactory templateFactory;
//
//     @Resource
//     private GetSqlUtil getSqlUtil;
//
//     @Resource
//     private RabbitTemplate rabbitTemplate;
//
//     @Resource
//     private UserTagSqlMapper userTagSqlMapper;
//
//     @Resource
//     private TagGroupLogMapper tagGroupLogMapper;
//
//     @Resource
//     private TagRefreshConstants tagRefreshConstants;
//     @Resource
//     private ErpProductService erpProductService;
//
//     @Resource
//     private TagDimensionMapper tagDimensionMapper;
//
//     // 全渠道
//     private final static String ALL_CHANNEL = "AllChannel";
//
//     // 医药馆
//     private final static String SIGO_YAO = "SigoYao";
//     private final static String SIGO_LAN_MOU = "SigoLanmou";
//     @Resource
//     private CrowdScheduleService crowdScheduleService;
//     @Resource
//     private TagRelationInfoDao tagRelationInfoDao;
//     @Resource
//     private RedisTemplate redisTemplate;
//
//     @Resource
//     @Lazy
//     private TagService tagService;
//
//     @Resource
//     private UserTagService userTagService;
//
//     @Resource
//     private TagCustomVipMapper tagCustomVipMapper;
//
//     @Resource
//     private TagGroupLogVipMapper tagGroupLogVipMapper;
//
//     @Resource
//     private TagGroupRelationCustomVipMapper tagGroupRelationCustomVipMapper;
//
//     @Resource
//     private TagGroupCustomVipMapper tagGroupCustomVipMapper;
//
//     @Resource
//     private TagRelationInfoVipDao tagRelationInfoVipDao;
//
//     @Resource
//     private AttributeConfCustomVipMapper attributeConfCustomVipMapper;
//
//     List<String> channels = new ArrayList<String>() {{
//         add("SigoWeb");
//         add("YYG");
//         add("YJH");
//         add("YJQ");
//         add("SigoLanmou");
//         add("SigoYao");
//         add("AllChannel");
//     }};
//
//
//     /**
//      * 生成渲染
//      *
//      * @return
//      */
//     @Override
//     public List<AttributeResponse> getTagRender(TagRenderRequest request) {
//         Integer tagGroupId = request.getTagGroupId();
//         //查询标签组，判断是否是自定义标签组，区分渲染方式
//         List<AttributeResponse> tree = new ArrayList<>();
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("getTagRender globalTagType: {}", globalTagType);
//         if (ObjectUtil.isNotNull(tagGroupId)) {
//             //查询属性表->内存中处理渲染逻辑
//             TagGroup tagGroupById;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupById = tagGroupCustomVipMapper.getTagGroupById(tagGroupId);
//             } else {
//                 tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//             }
//             if (tagGroupById != null) {
//                 Integer attributeModelType = tagGroupById.getAttributeModelType();
//                 Integer tagGroupSource = tagGroupById.getTagGroupSource();
//                 Integer isInitData = tagGroupById.getIsInitData();
//                 boolean useForCrowd = request.getTagUseType() != null && request.getTagUseType() == 1;
//                 // 人群-标签渲染数据范围
//                 if (useForCrowd) {
//                     getDataRange(tree, tagGroupById);
//                 }
//                 if ((tagGroupSource == 0 && isInitData == 0) || tagGroupSource == 2) {
//                     //不是系统并且不是自定义，查询渲染表跟关系表
//                     //如果没有初始化数据的系统标签组，需要走模板表跟关系表
//                     tree.addAll(getAttributeTree(attributeModelType, globalTagType));
//                 } else {
//                     //自定义标签组，或者有初始化数据的系统标签组
//                     //查询tag表获取分层
//                     List<Tag> byTagGroupId;
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         byTagGroupId = tagCustomVipMapper.getByTagGroupId(request.getTagGroupId());
//                     } else {
//                         byTagGroupId = tagCustomMapper.getByTagGroupId(request.getTagGroupId());
//                     }
//                     if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                         //排除停止更新的标签
//                         byTagGroupId = byTagGroupId.stream().filter(t -> t.getDealStatus() != 4).collect(Collectors.toList());
//                         if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                             AttributeResponse response = new AttributeResponse();
//                             response.setAttributeName(tagGroupById.getTagGroupName());
//                             if (tagGroupSource == 0 && isInitData == 1) {
//                                 List<AttributeConf> attributeConfByTagGroupId;
//                                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                                     // 会员标签
//                                     attributeConfByTagGroupId = attributeConfCustomVipMapper.getAttributeConfByTagGroupId(attributeModelType);
//                                 } else {
//                                     attributeConfByTagGroupId = attributeConfCustomMapper.getAttributeConfByTagGroupId(attributeModelType);
//                                 }
//                                 if (CollectionUtil.isNotEmpty(attributeConfByTagGroupId)) {
//                                     AttributeConf conf = attributeConfByTagGroupId.get(0);
//                                     response.setComponentType(conf.getComponentType());
//                                 } else {
//                                     response.setComponentType("checkbox");
//                                 }
//                             } else {
//                                 response.setComponentType("checkbox");
//                             }
//                             response.setAttributeId("group" + tagGroupId);
//                             response.setShouldAdd(1);
//                             List<AttributeResponse> childs = new ArrayList<>();
//                             for (Tag tag : byTagGroupId) {
//                                 AttributeResponse child = new AttributeResponse();
//                                 child.setAttributeId(tag.getId());
//                                 child.setAttributeName(tag.getTagName());
//                                 child.setComponentType("value");
//                                 childs.add(child);
//                             }
//                             // 人群-标签 超过10个 下拉多选
//                             if (childs.size() > 10) {
//                                 response.setComponentType("selectMultiple");
//                             }
//                             response.setChilds(childs);
//                             tree.add(response);
//                         }
//                     }
//                 }
//             }
//         } else if (ObjectUtil.isNotNull(request.getCustomType()) && request.getCustomType() == 2) {
//             //事件偏好属性
//             tree = getAttributeTree(14, globalTagType);
//         }
//
//         //重复引用会出现$ref
//         String result = JSON.toJSONString(tree, SerializerFeature.DisableCircularReferenceDetect);
//         return JSON.parseArray(result, AttributeResponse.class);
//
//     }
//
//
//     /**
//      * 获取标签筛选项 的 数据范围-渠道
//      * select、多选必填、渠道
//      *
//      * @return
//      */
//     private void getDataRange(List<AttributeResponse> tree, TagGroup group) {
//         //判断是否标签组引用的内部标签组是否是只支持单渠道单店铺
//         Boolean limitRange = false;
//         //限制基础标签id集合
//         List<Integer> limitRangeIdList = new ArrayList<>();
//         List<TagGroup> limitRangeTagGroup = tagGroupCustomMapper.getLimitRangeTagGroup();
//         if (CollectionUtil.isNotEmpty(limitRangeTagGroup)) {
//             limitRangeIdList = limitRangeTagGroup.stream().map(TagGroup::getId).collect(Collectors.toList());
//         }
//
//         if (group.getLimitDataRange() != null && group.getLimitDataRange() == 1) {
//             limitRange = true;
//         } else {
//             //查看内部引用的标签是否限制渠道
//             Integer id = group.getId();
//             List<Tag> byTagGroupId = tagCustomMapper.getByTagGroupId(id);
//             if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                 for (Tag tag : byTagGroupId) {
//                     //遍历分层，看使用的标签组id是否限制了渠道
//                     if (StrUtil.isNotBlank(tag.getExpression())) {
//                         List<TagGroupExpressionRequest> expressions = JSON.parseArray(tag.getExpression(), TagGroupExpressionRequest.class);
//                         if (CollectionUtil.isNotEmpty(expressions)) {
//                             List<Integer> groupIds = expressions.stream().map(TagGroupExpressionRequest::getTagGroupId).distinct().collect(Collectors.toList());
//                             if (CollectionUtil.isNotEmpty(groupIds)) {
//                                 //过滤出需要限制的标签
//                                 List<Integer> finalLimitRangeIdList = limitRangeIdList;
//                                 List<Integer> canLimitIds = groupIds.stream().filter(g -> finalLimitRangeIdList.contains(g)).collect(Collectors.toList());
//                                 if (CollectionUtil.isNotEmpty(canLimitIds)) {
//                                     //如果存在限制渠道
//                                     limitRange = true;
//                                     break;
//                                 } else {
//                                     //如果不存在，继续往下看嵌套的标签组内是否存在限制
//                                     List<TagGroup> tagGroupByIds = tagGroupCustomMapper.getTagGroupByIds(groupIds);
//                                     if (CollectionUtil.isNotEmpty(tagGroupByIds)) {
//                                         //过滤出未初始化的数据,目前初始化的数据是不会到店铺的
//                                         List<TagGroup> customList = tagGroupByIds.stream().filter(t -> t.getCustomType() != null && t.getCustomType() == 1).collect(Collectors.toList());
//                                         if (CollectionUtil.isNotEmpty(customList)) {
//                                             //查询下级引用
//                                             List<Integer> customIds = customList.stream().map(TagGroup::getId).collect(Collectors.toList());
//                                             if (CollectionUtil.isNotEmpty(customIds)) {
//                                                 for (Integer customId : customIds) {
//                                                     List<TagGroupUseRelation> tagGroupUseRelation = tagGroupRelationCustomMapper.getTagGroupUseRelation(customId);
//                                                     if (CollectionUtil.isNotEmpty(tagGroupUseRelation)) {
//                                                         List<Integer> useTagIds = tagGroupUseRelation.stream().map(TagGroupUseRelation::getUseTagId).collect(Collectors.toList());
//                                                         List<Tag> byTagIds = tagCustomMapper.getByTagIds(useTagIds);
//                                                         for (Tag inTag : byTagIds) {
//                                                             List<TagGroupExpressionRequest> inExpressions = JSON.parseArray(inTag.getExpression(), TagGroupExpressionRequest.class);
//                                                             if (CollectionUtil.isNotEmpty(inExpressions)) {
//                                                                 List<Integer> inGroupIds = inExpressions.stream().map(TagGroupExpressionRequest::getTagGroupId).distinct().collect(Collectors.toList());
//                                                                 if (CollectionUtil.isNotEmpty(inGroupIds)) {
//                                                                     List<Integer> finalLimitRangeIdList1 = limitRangeIdList;
//                                                                     List<Integer> inCanLimitIds = inGroupIds.stream().filter(g -> finalLimitRangeIdList1.contains(g)).collect(Collectors.toList());
//                                                                     if (CollectionUtil.isNotEmpty(inCanLimitIds)) {
//                                                                         //限制渠道
//                                                                         limitRange = true;
//                                                                     }
//                                                                 }
//                                                             }
//                                                         }
//                                                     }
//                                                 }
//                                             }
//                                         }
//                                     }
//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//
//
//         // 人群管理 引用标签的数据范围 需要处理
//         if (ALL_CHANNEL.equals(group.getDataRange())) {
//             // 全渠道 需要增加渠道、店铺的筛选
//             tree.add(getDataRange(group.getId()));
//             AttributeResponse storeRange = getStoreRange(group.getId());
//             if (limitRange) {
//                 storeRange.setComponentType("oneTreeMultiple");
//             }
//             tree.add(storeRange);
//         } else if (SIGO_YAO.equals(group.getDataRange()) || SIGO_LAN_MOU.equals(group.getDataRange())) {
//             // 全渠道 需要增加店铺的筛选
//             AttributeResponse storeRange = getStoreRange(group.getId());
//             if (limitRange) {
//                 storeRange.setComponentType("oneTreeMultiple");
//             }
//             tree.add(storeRange);
//         }
//     }
//
//
//     private AttributeResponse getDataRange(Integer groupId) {
//         AttributeResponse response = new AttributeResponse();
//         response.setAttributeId("dataRange" + groupId);
//         response.setAttributeName("数据范围");
//         response.setAttributeCode("ChannelTypeCode");
//         // 一级渠道
//         response.setChilds(getChannelDataRange());
//         response.setComponentType("selectMultiple");
//         response.setShouldAdd(1);
//         return response;
//     }
//
//
//     /**
//      * 获取渠道筛选项
//      *
//      * @return
//      */
//     private List<AttributeResponse> getChannelDataRange() {
//         List<AttributeResponse> list = new LinkedList<>();
//         for (EnumCrowdChannel channel : EnumCrowdChannel.values()) {
//             AttributeResponse response = new AttributeResponse();
//             response.setAttributeId(channel.getChannelKey());
//             response.setAttributeName(channel.getChannelValue());
//             list.add(response);
//         }
//         return list;
//     }
//
//     /**
//      * 获取标签筛选项 的 数据范围-店铺
//      * tree、多选必填、店铺
//      *
//      * @return
//      */
//     private AttributeResponse getStoreRange(Integer groupId) {
//         AttributeResponse response = new AttributeResponse();
//         response.setAttributeId("storeTree" + groupId);
//         response.setAttributeName("数据范围");
//         response.setAttributeCode("ChannelStoreCode");
//         response.setChilds(new ArrayList<>());
//         response.setComponentType("treeMultiple");
//         response.setShouldAdd(1);
//         return response;
//     }
//
//
//     public List<AttributeResponse> getAttributeTree(Integer attributeModelType, Integer globalTagType) {
//         List<AttributeResponse> tree = new ArrayList<>();
//         List<AttributeConf> attributeConf;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             attributeConf = attributeConfCustomVipMapper.getAttributeConfAndRelations(attributeModelType);
//         } else {
//             attributeConf = attributeConfCustomMapper.getAttributeConfAndRelations(attributeModelType);
//         }
//         List<AttributeResponse> all = new ArrayList<>();
//         List<AttributeResponse> root = new ArrayList<>();
//         if (CollectionUtil.isNotEmpty(attributeConf)) {
//             AttributeResponse response;
//             //所有
//             for (AttributeConf conf : attributeConf) {
//                 response = new AttributeResponse();
//                 copyAttribute(response, conf);
//                 all.add(response);
//             }
//             //排序父级
//             List<AttributeConf> rootConf = attributeConf.stream().filter(e -> e.getPid() == 0).sorted(Comparator.comparing(AttributeConf::getSort)).collect(Collectors.toList());
//             for (AttributeConf conf : rootConf) {
//                 response = new AttributeResponse();
//                 copyAttribute(response, conf);
//                 root.add(response);
//             }
//             //调用递归方法生成
//             for (AttributeResponse confResponse : root) {
//                 confResponse = buildChildTree(confResponse, all);
//                 tree.add(confResponse);
//             }
//         }
//         return tree;
//     }
//
//     public void copyAttribute(AttributeResponse response, AttributeConf conf) {
//         response.setAttributeId(conf.getId());
//         response.setAttributeValue(conf.getAttributeValue());
//         response.setAttributeCode(conf.getAttributeCode());
//         response.setPId(conf.getPid());
//         response.setRules(JSON.parseArray(conf.getRules(), Map.class));
//         response.setAttributeName(conf.getAttributeName());
//         response.setUnit(conf.getUnit());
//         response.setIsEventObject(conf.getIsEventObject());
//         response.setComponentType(conf.getComponentType());
//         response.setLinkApi(JSON.parseObject(conf.getLinkApi()));
//         response.setSqlLocation(conf.getSqlLocation());
//         response.setHiddenId(JSON.parseArray(conf.getHiddenId(), Object.class));
//         response.setEffectId(JSON.parseArray(conf.getEffectId(), Object.class));
//         response.setTemplatePart(conf.getTemplatePart());
//         response.setShouldAdd(conf.getShouldAdd());
//         response.setGpTablePart(conf.getGpTablePart());
//         response.setGpColumnName(conf.getGpColumnName());
//     }
//
//     /**
//      * 递归生成树
//      *
//      * @param field
//      * @param list
//      * @return
//      */
//     public AttributeResponse buildChildTree(AttributeResponse field, List<AttributeResponse> list) {
//         List<AttributeResponse> childList = new ArrayList<>();
//         if (CollectionUtil.isNotEmpty(list)) {
//             for (AttributeResponse response : list) {
//                 if (response.getPId().equals(field.getAttributeId())) {
//                     childList.add(buildChildTree(response, list));
//                 }
//             }
//         }
//         field.setChilds(childList);
//         return field;
//     }
//
//
//     @Override
//     public CommonResponse checkExpression(TagExpressionRequest requests) {
//         //验证引用关系
//         checkRelation(requests.getExpression(), requests.getGlobalTagType());
//         //验证公式
//         return new CommonResponse(checkEx(requests.getExpression()));
//     }
//
//
//     /**
//      * 验证标签组引用关系，引用数量不能大于3，不支持嵌套引用
//      */
//     public void checkRelation(List<TagGroupExpressionRequest> requests, Integer globalTagType) {
//         if (CollectionUtil.isNotEmpty(requests)) {
//             List<TagGroupExpressionRequest> collect = requests.stream().filter(e -> TAG_GROUP.getType().equals(e.getType()) && 1 == e.getTagGroupSource()).collect(Collectors.toList());
//             if (CollectionUtil.isNotEmpty(collect) && collect.size() > tagRefreshConstants.getInUseTagNumLimit()) {
//                 throw new Http400Exception("tag_count_gt3_error", "引用数量不能大于" + tagRefreshConstants.getInUseTagNumLimit() + "，全渠道引用数量按照当前数量*4进行计算！");
//             }
//             //遍历展开自定义标签
//             for (TagGroupExpressionRequest request : requests) {
//                 if (TAG_GROUP.getType().equals(request.getType()) && 1 == request.getTagGroupSource()) {
//                     TagGroup tagGroup;
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagGroup = tagGroupCustomVipMapper.getTagGroupById(request.getTagGroupId());
//                     } else {
//                         tagGroup = tagGroupCustomMapper.getTagGroupById(request.getTagGroupId());
//                     }
//                     if (tagGroup == null) {
//                         throw new Http400Exception("get_tag_group_by_expression_error", "标签组不存在");
//                     }
//                     //查询此标签组是否引用了其他标签组
//                     List<TagGroupUseRelation> tagGroupUseRelation;
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagGroupUseRelation = tagGroupRelationCustomVipMapper.getTagGroupUseRelation(request.getTagGroupId());
//                     } else {
//                         tagGroupUseRelation = tagGroupRelationCustomMapper.getTagGroupUseRelation(request.getTagGroupId());
//                     }
//                     if (CollectionUtil.isNotEmpty(tagGroupUseRelation)) {
//                         throw new Http400Exception("nestedError", "标签组[" + tagGroup.getTagGroupName() + "]内已引用其他标签组，不支持嵌套引用");
//                     }
//                 }
//             }
//         }
//     }
//
//
//     /**
//      * 校验公式是否合法
//      *
//      * @param requests
//      * @return
//      */
//     public boolean checkEx(List<TagGroupExpressionRequest> requests) {
//         TagGroupExpressionRequest previous = new TagGroupExpressionRequest();
//         List<TagGroupExpressionRequest> openOrCloseList = new ArrayList<>();
//         if (CollectionUtil.isNotEmpty(requests)) {
//             String type = requests.get(requests.size() - 1).getType();
//             String symbol = requests.get(requests.size() - 1).getSymbol();
//             if (!(TAG_GROUP.getType().equals(type) || (SYMBOL.getType().equals(type) && CLOSE_PAREN.getExpression().equals(symbol)))) {
//                 //判断最后一个是否是标签组或者标签或者是右括号
//                 return false;
//             }
//             if (requests.size() == 1 && !((TAG_GROUP.getType().equals(requests.get(0).getType())))) {
//                 //单个标签
//                 return false;
//             } else if (requests.size() > 1 && requests.size() % 2 != 0) {
//                 //除去括号不能是偶数
//                 //符号比标签少一个
//                 for (int i = 0; i < requests.size(); i++) {
//                     TagGroupExpressionRequest request = requests.get(i);
//                     if (i == 0) {
//                         //如果第一个如果不是左括号标签组 错误
//                         if (!(TAG_GROUP.getType().equals(request.getType())
//                                 || (SYMBOL.getType().equals(request.getType()) && OPEN_PAREN.getExpression().equals(request.getSymbol())))
//                         ) {
//                             return false;
//                         }
//                         if (SYMBOL.getType().equals(request.getType()) && OPEN_PAREN.getExpression().equals(request.getSymbol())) {
//                             openOrCloseList.add(request);
//                         }
//                     } else {
//                         if (SYMBOL.getType().equals(request.getType()) && OPEN_PAREN.getExpression().equals(request.getSymbol())) {
//                             //左括号判断上一个不能是右括号，不能是标签跟标签组（只能是符号跟左括号）
//                             if (!(SYMBOL.getType().equals(previous.getType())
//                                     && (INTERSECT.getExpression().equals(previous.getSymbol())
//                                     || UNION.getExpression().equals(previous.getSymbol())
//                                     || MINUS.getExpression().equals(previous.getSymbol())
//                                     || OPEN_PAREN.getExpression().equals(previous.getSymbol())))) {
//                                 return false;
//                             }
//                             openOrCloseList.add(request);
//                         }
//                         if (SYMBOL.getType().equals(request.getType()) && CLOSE_PAREN.getExpression().equals(request.getSymbol())) {
//                             //如果是右括号判断上一个只能是标签或标签组,如果是公式除右括号错误
//                             if (SYMBOL.getType().equals(previous.getType()) &&
//                                     (INTERSECT.getExpression().equals(previous.getSymbol())
//                                             || UNION.getExpression().equals(previous.getSymbol())
//                                             || MINUS.getExpression().equals(previous.getSymbol())
//                                             || OPEN_PAREN.getExpression().equals(previous.getSymbol()))
//                             ) {
//                                 return false;
//                             }
//                             openOrCloseList.add(request);
//                         }
//
//                         if (TAG_GROUP.getType().equals(request.getType())) {
//                             //如果是标签或标签组，判断上一个，如果是标签组标签跟右括号,错误
//                             if (TAG_GROUP.getType().equals(previous.getType())
//                                     || (SYMBOL.getType().equals(previous.getType()) && CLOSE_PAREN.getExpression().equals(previous.getSymbol()))
//                             ) {
//                                 return false;
//                             }
//                         }
//
//                         if (SYMBOL.getType().equals(request.getType()) && (INTERSECT.getExpression().equals(request.getSymbol())
//                                 || UNION.getExpression().equals(request.getSymbol()) || MINUS.getExpression().equals(request.getSymbol()))) {
//                             //如果是交并差符号，判断上一个，如果同样是交并差符号错误
//                             if (SYMBOL.getType().equals(previous.getType()) && (INTERSECT.getExpression().equals(previous.getSymbol())
//                                     || UNION.getExpression().equals(previous.getSymbol()) || MINUS.getExpression().equals(previous.getSymbol()))
//                             ) {
//                                 return false;
//                             }
//                         }
//                     }
//                     previous = request;
//
//                 }
//                 //判断括号是否成对
//                 int openOrCloseSize = openOrCloseList.size();
//                 if (openOrCloseSize > 0) {
//                     if (openOrCloseSize % 2 != 0) {
//                         return false;
//                     }
//                     //判断括号是否顺序正确
//                     if (!checkBrackets(openOrCloseList)) {
//                         return false;
//                     }
//                 }
//             } else if (requests.size() > 1 && requests.size() % 2 == 0) {
//                 //表达式为偶数个错误
//                 return false;
//             }
//         } else {
//             return false;
//         }
//         return true;
//     }
//
//
//     /**
//      * 校验括号成对
//      *
//      * @param openOrCloseList
//      * @return
//      */
//     public boolean checkBrackets(List<TagGroupExpressionRequest> openOrCloseList) {
//         if (CollectionUtil.isEmpty(openOrCloseList)) {
//             return false;
//         }
//         List<String> list = new ArrayList<>();
//         Map<String, String> map = new HashMap<>(16);
//         map.put(OPEN_PAREN.getExpression(), CLOSE_PAREN.getExpression());
//
//         for (int i = 0; i < openOrCloseList.size(); i++) {
//             String brackets = openOrCloseList.get(i) == null ? "" : openOrCloseList.get(i).getSymbol();
//             if (map.containsKey(brackets)) {
//                 list.add(brackets);
//             } else if (map.containsValue(brackets)) {
//                 if (list.isEmpty() || !brackets.equals(map.get(list.get(list.size() - 1)))) {
//                     return false;
//                 } else {
//                     list.remove(list.size() - 1);
//                 }
//             }
//         }
//         return list.isEmpty();
//     }
//
//
//     @Override
//     public TagCountResponse getProbablyCount(TagExpressionRequest requests) {
//         Integer globalTagType = requests.getGlobalTagType();
//         log.info("getProbablyCount globalTagType: {}", globalTagType);
//         TagCountResponse response = new TagCountResponse();
//         List<TagGroupExpressionRequest> expressions = requests.getExpression();
//         if (CollUtil.isNotEmpty(expressions)) {
//             expressions.forEach(expression -> expression.setGlobalTagType(globalTagType));
//             String queryBuilder = buildQueryAllChannel(requests.getExpression(), requests.getDataRange());
//             //调用gp查询count
//             try {
//                 Integer count = userTagSqlMapper.getCount(queryBuilder);
//                 response.setCount(count);
//                 // 返回新的sql
//                 response.setTagDimensionQuerySql(queryBuilder);
//             } catch (Exception e) {
//                 log.error("预估人数查询gp失败,入参:[{}],SQL:[{}],异常:[{}]", JSON.toJSONString(requests), queryBuilder, e);
//                 throw new Http400Exception("queryError", "查询失败");
//             }
//         }
//         return response;
//     }
//
//     @Override
//     @Transactional(rollbackFor = Exception.class)
//     public CommonResponse createTagGroup(CreateTagGroupRequest request) {
//         //判断名称是否重复
//         TagGroupNameRequest tagGroupNameRequest = new TagGroupNameRequest();
//         tagGroupNameRequest.setTagGroupName(request.getTagGroupName());
//         tagGroupNameRequest.setChannelCode(request.getChannelCode());
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("createTagGroup globalTagType: {}", globalTagType);
//         int i;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             i = tagGroupCustomVipMapper.getTagGroupByName(tagGroupNameRequest);
//         } else {
//             i = tagGroupCustomMapper.getTagGroupByName(tagGroupNameRequest);
//         }
//         if (i > 0) {
//             throw new Http400Exception("duplicateName", "名称重复");
//         }
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setTagGroupName(request.getTagGroupName());
//         tagGroup.setTagGroupCateId(request.getTagGroupCateId());
//         tagGroup.setTagGroupSource(1);
//         tagGroup.setCreateWay(request.getCreateWay());
//         tagGroup.setCustomType(request.getCustomType());
//         tagGroup.setRemark(request.getRemark());
//         tagGroup.setCreateTime(new Date());
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroup.setSort(0);
//         tagGroup.setCreater(request.getUserId());
//         tagGroup.setCreaterName(request.getUserName());
//         tagGroup.setExpression(JSONObject.toJSONString(request.getTags()));
//         tagGroup.setDataRange(request.getDataRange());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupCustomVipMapper.insertTagGroup(tagGroup);
//         } else {
//             tagGroupCustomMapper.insertTagGroup(tagGroup);
//         }
//         List<TagHierarchyRequest> tags = request.getTags();
//         if (CollectionUtil.isEmpty(tags)) {
//             throw new Http400Exception("pleaseAddLayer", "请添加分层");
//         }
//         List<TagGroupUseRelation> relationList = new ArrayList<>();
//         for (TagHierarchyRequest tagHierarchyRequest : tags) {
//             List<TagGroupExpressionRequest> calLogic = tagHierarchyRequest.getCalLogic();
//             // 设置全局标签类型
//             calLogic.forEach(tag -> tag.setGlobalTagType(globalTagType));
//             boolean b = checkEx(calLogic);
//             if (!b) {
//                 throw new Http400Exception("layerExpressionIllegal", "分层[" + tagHierarchyRequest.getName() + "]公式不合法");
//             }
//             String sql = buildQueryAllChannel(calLogic, request.getDataRange());
//             if (StrUtil.isBlank(sql)) {
//                 throw new Http400Exception("layerExpressionGenerateError", "分层[" + tagHierarchyRequest.getName() + "]公式生成错误");
//             }
//             Tag tag = new Tag();
//             tag.setTagName(tagHierarchyRequest.getName());
//             tag.setDescribe(tagHierarchyRequest.getDescription());
//             tag.setTagGroupId(tagGroup.getId());
//             tag.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//             tag.setSqlQuery(sql);
//             tag.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//             tag.setChannelCode(request.getChannelCode());
//             tag.setChannelLevel(request.getChannelLevel());
//             tag.setLayerCount(tagHierarchyRequest.getLayerCount());
//             tag.setCountUpdateTime(new Date());
//             tag.setSort(tagHierarchyRequest.getSort());
//             tag.setCreateTime(new Date());
//             tag.setCreater(request.getUserId());
//             tag.setCreaterName(request.getUserName());
//             tag.setIsDeleted(0);
//             //分层循环新增,需要返回的主键id做后续操作
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagCustomVipMapper.insertTag(tag);
//             } else {
//                 tagCustomMapper.insertTag(tag);
//             }
//             try {
//                 // 级联更新的编辑标志
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     redisTemplate.opsForValue().set(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                 } else {
//                     redisTemplate.opsForValue().set(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                 }
//             } catch (Exception e) {
//                 log.error("set redis fail, globalTagType: {}, tagId: {}, error: {}", globalTagType, tag.getId(), ExceptionUtils.getStackTrace(e));
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     redisTemplate.delete(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId());
//                 } else {
//                     redisTemplate.delete(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId());
//                 }
//             }
//             List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//             for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                 if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                     List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                     TagGroupSelect select = selects.get(0);
//                     Object value = select.getValue();
//                     if (ObjectUtil.isNotNull(value)) {
//                         List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                         TagGroupUseRelation relation;
//                         for (Integer id : listValue) {
//                             relation = new TagGroupUseRelation();
//                             relation.setTagId(tag.getId());
//                             relation.setTagGroupId(tagGroup.getId());
//                             relation.setUseTagId(id);
//                             relation.setUseTagGroupId(tagGroupRequest.getTagGroupId());
//                             relation.setIsDeleted(0);
//                             relationList.add(relation);
//                         }
//                     }
//                 }
//             }
//         }
//         //插入引用
//         if (CollectionUtil.isNotEmpty(relationList)) {
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationList);
//             } else {
//                 tagGroupRelationCustomMapper.insertTagGroupRelation(relationList);
//             }
//         }
//         TagGroupLog tagGroupLog = new TagGroupLog();
//         tagGroupLog.setTagGroupId(tagGroup.getId());
//         tagGroupLog.setActionType(TagGroupActionEnum.CREATE.getAction());
//         tagGroupLog.setActionTime(new Date());
//         tagGroupLog.setCreater(request.getUserId());
//         tagGroupLog.setCreaterName(request.getUserName());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupLogVipMapper.insertLog(tagGroupLog);
//             //发mq
//             sendMsg(VIP_TAG_QUEUE, tagGroup.getId());
//         } else {
//             tagGroupLogMapper.insertLog(tagGroupLog);
//             //发mq
//             sendMsg(TAG_QUEUE, tagGroup.getId());
//         }
//         return new CommonResponse(true);
//     }
//
//     @Override
//     @Transactional(rollbackFor = Exception.class)
//     public CommonResponse createEventTagGroup(CreateEventTagGroupRequest request) {
//         //判断名称是否重复
//         TagGroupNameRequest tagGroupNameRequest = new TagGroupNameRequest();
//         tagGroupNameRequest.setTagGroupName(request.getTagGroupName());
//         tagGroupNameRequest.setChannelCode(request.getChannelCode());
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("createEventTagGroup globalTagType: {}", globalTagType);
//         int i;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             i = tagGroupCustomVipMapper.getTagGroupByName(tagGroupNameRequest);
//         } else {
//             i = tagGroupCustomMapper.getTagGroupByName(tagGroupNameRequest);
//         }
//         if (i > 0) {
//             throw new Http400Exception("duplicateName", "名称重复");
//         }
//         List<TagGroupExpressionRequest> calLogic = request.getCalLogic();
//         boolean b = checkEx(calLogic);
//         if (!b) {
//             throw new Http400Exception("eventExpressionIllegal", "公式不合法");
//         }
//         if (CollectionUtil.isEmpty(calLogic)) {
//             throw new Http400Exception("paramError", "参数错误");
//         }
//         //获取
//         TagGroupExpressionRequest expressionRequest = calLogic.get(0);
//         //偏好单个公式
//         List<TagGroupSelect> tagGroupSelects = expressionRequest.getSelects();
//
//         if (CollectionUtil.isEmpty(tagGroupSelects)) {
//             throw new Http400Exception("pleaseChoose", "请选择筛选项");
//         }
//         //处理内部分层
//         List<TagGroupSelect> calLogicEvent = tagGroupSelects.stream().filter(c -> ObjectUtil.isNotNull(c.getIsEventObject()) && c.getIsEventObject() == 1).collect(Collectors.toList());
//         if (CollectionUtil.isEmpty(calLogicEvent)) {
//             throw new Http400Exception("pleaseAddEventObject", "请选择行为对象");
//         }
//         List<TagGroupSelect> calLogicNotEvent = tagGroupSelects.stream().filter(c -> ObjectUtil.isNull(c.getIsEventObject()) || c.getIsEventObject() == 0).collect(Collectors.toList());
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setTagGroupName(request.getTagGroupName());
//         tagGroup.setTagGroupCateId(request.getTagGroupCateId());
//         tagGroup.setTagGroupSource(1);
//         tagGroup.setCreateWay(request.getCreateWay());
//         tagGroup.setCustomType(request.getCustomType());
//         tagGroup.setExpression(JSON.toJSONString(calLogic));
//         tagGroup.setRemark(request.getRemark());
//         tagGroup.setCreateTime(new Date());
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroup.setSort(0);
//         tagGroup.setCreater(request.getUserId());
//         tagGroup.setCreaterName(request.getUserName());
//         tagGroup.setDataRange(request.getDataRange());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupCustomVipMapper.insertTagGroup(tagGroup);
//         } else {
//             tagGroupCustomMapper.insertTagGroup(tagGroup);
//         }
//         TagGroupSelect tagGroupSelect = calLogicEvent.get(0);
//         List<Object> value = (List<Object>) tagGroupSelect.getValue();
//
//         Integer attributeId = (Integer) tagGroupSelect.getAttributeId();
//         if (ObjectUtil.isNull(attributeId)) {
//             throw new Http400Exception("attributeId is null", "attributeId为空");
//         }
//         //根据id获取
//         AttributeConf attributeConf;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             attributeConf = attributeConfCustomVipMapper.getConfigById(attributeId);
//         } else {
//             attributeConf = attributeConfCustomMapper.getConfigById(attributeId);
//         }
//         if (ObjectUtil.isNull(attributeConf)) {
//             throw new Http400Exception("attributeConf is null", "attributeConf为空");
//         }
//         String linkApi = attributeConf.getLinkApi();
//         if (StrUtil.isBlank(linkApi)) {
//             throw new Http400Exception("linkApi is null", "linkApi为空");
//         }
//         String attributeCode = attributeConf.getAttributeCode();
//         if (StrUtil.isBlank(attributeCode)) {
//             throw new Http400Exception("attributeCode is null", "attributeCode为空");
//         }
//         Integer objType = null;
//         if ("cate".equals(attributeCode)) {
//             objType = 2;
//         } else if ("brand".equals(attributeCode)) {
//             objType = 1;
//         } else if ("product".equals(attributeCode)) {
//             objType = 0;
//         } else if ("cycle".equals(attributeCode)) {
//             objType = 3;
//         } else if ("degree".equals(attributeCode)) {
//             objType = 4;
//         }
//         if (objType == null) {
//             throw new Http400Exception("objType is null", "objType为空");
//         }
//         List<TagHierarchyRequest> tags = request.getTags();
//         if (CollectionUtil.isEmpty(tags)) {
//             //模拟编辑参数
//             String param = JSON.toJSONString(request);
//             EditEventTagGroupRequest editEventTagGroupRequest = JSON.parseObject(param, EditEventTagGroupRequest.class);
//             editEventTagGroupRequest.setTagGroupId(tagGroup.getId());
//             tagGroup.setEventParam(JSON.toJSONString(editEventTagGroupRequest));
//             tagGroup.setIsNotLimit(1);
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//             } else {
//                 tagGroupCustomMapper.updateTagGroupById(tagGroup);
//             }
//             //如果为空说明是不限
//             if (CollectionUtil.isNotEmpty(value)) {
//                 throw new Http400Exception("", "数据有误");
//             }
//             JSONObject linkApiJson = JSONObject.parseObject(linkApi);
//             JSONArray data = linkApiJson.getJSONArray("data");
//             if (CollectionUtil.isNotEmpty(data)) {
//                 //获取data
//                 List<TagHierarchyRequest> tagsData = new ArrayList<>();
//                 for (Object inData : data) {
//                     //数据
//                     JSONObject inDataJson = (JSONObject) inData;
//                     Object attributeValue = inDataJson.get("attributeValue");
//                     String attributeName = inDataJson.getString("attributeName");
//                     //组装分层信息
//                     TagHierarchyRequest tagHierarchyRequest = new TagHierarchyRequest();
//                     tagHierarchyRequest.setName(attributeName);
//                     TagGroupExpressionRequest tagGroupExpressionRequest = new TagGroupExpressionRequest();
//                     tagGroupExpressionRequest.setChannelLevel(request.getChannelLevel());
//                     tagGroupExpressionRequest.setChannelCode(request.getChannelCode());
//                     tagGroupExpressionRequest.setType("tagGroup");
//                     tagGroupExpressionRequest.setTagGroupSource(2);
//                     Object[] selectValue = new Object[]{attributeValue};
//                     tagGroupSelect.setValue(selectValue);
//                     tagGroupExpressionRequest.setSelects(Collections.singletonList(tagGroupSelect));
//                     tagHierarchyRequest.setCalLogic(Collections.singletonList(tagGroupExpressionRequest));
//                     TagHierarchyRequest tagHierarchyRequestCopy = JSON.parseObject(JSON.toJSONString(tagHierarchyRequest), TagHierarchyRequest.class);
//                     tagsData.add(tagHierarchyRequestCopy);
//                 }
//                 //小于1000，直接调用
//                 dealTags(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                 //发mq
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_TAG_QUEUE, tagGroup.getId());
//                 } else {
//                     sendMsg(TAG_QUEUE, tagGroup.getId());
//                 }
//             } else {
//                 //如果没有data,调用方法
//                 //调用方法需要分页调用，调用，一千一次
//                 String apiUrl = linkApiJson.getString("url");
//                 if (StrUtil.isNotBlank(apiUrl)) {
//                     //扔mq，直接返回
//                     DelayEventTagRequest delayCreateEventTagRequest = new DelayEventTagRequest();
//                     delayCreateEventTagRequest.setCreateRequest(request);
//                     delayCreateEventTagRequest.setCalLogicNotEvent(calLogicNotEvent);
//                     delayCreateEventTagRequest.setTagGroup(tagGroup);
//                     delayCreateEventTagRequest.setApi(linkApi);
//                     delayCreateEventTagRequest.setTagGroupSelect(tagGroupSelect);
//                     delayCreateEventTagRequest.setType(0);
//                     delayCreateEventTagRequest.setObjType(objType);
//                     //处理时间跟动作类型
//                     doTypeAndTime(tagGroupSelects, delayCreateEventTagRequest);
//                     //发mq
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         sendMsg(VIP_EVENT_TAG_QUEUE, JSON.toJSONString(delayCreateEventTagRequest));
//                     } else {
//                         sendMsg(EVENT_QUEUE, JSON.toJSONString(delayCreateEventTagRequest));
//                     }
//                     //发送完mq状态修改为创建中
//                     TagGroup tagGroupCopy = new TagGroup();
//                     tagGroupCopy.setId(tagGroup.getId());
//                     tagGroupCopy.setDealStatus(TagGroupDelStatusEnum.IN_THE_CREATE.getDealStatus());
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagGroupCustomVipMapper.updateTagGroupById(tagGroupCopy);
//                     } else {
//                         tagGroupCustomMapper.updateTagGroupById(tagGroupCopy);
//                     }
//                 }
//             }
//         } else {
//             if (tags.size() > 1000) {
//                 //大于1000，扔mq，直接返回
//                 DelayEventTagRequest delayCreateEventTagRequest = new DelayEventTagRequest();
//                 delayCreateEventTagRequest.setCreateRequest(request);
//                 delayCreateEventTagRequest.setCalLogicNotEvent(calLogicNotEvent);
//                 delayCreateEventTagRequest.setTagGroup(tagGroup);
//                 delayCreateEventTagRequest.setType(0);
//                 delayCreateEventTagRequest.setObjType(objType);
//                 // 发送mq
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_EVENT_TAG_QUEUE, JSON.toJSONString(delayCreateEventTagRequest));
//                 } else {
//                     sendMsg(EVENT_QUEUE, JSON.toJSONString(delayCreateEventTagRequest));
//                 }
//                 //发送完mq状态修改为创建中
//                 TagGroup tagGroupCopy = new TagGroup();
//                 tagGroupCopy.setId(tagGroup.getId());
//                 tagGroupCopy.setDealStatus(TagGroupDelStatusEnum.IN_THE_CREATE.getDealStatus());
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagGroupCustomVipMapper.updateTagGroupById(tagGroupCopy);
//                 } else {
//                     tagGroupCustomMapper.updateTagGroupById(tagGroupCopy);
//                 }
//             } else {
//                 //小于1000，直接调用
//                 dealTags(request, calLogicNotEvent, tagGroup, tags, objType);
//                 //发mq
//
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_TAG_QUEUE, tagGroup.getId());
//                 } else {
//                     sendMsg(TAG_QUEUE, tagGroup.getId());
//                 }
//             }
//         }
//         return new CommonResponse(true);
//     }
//
//     private void doTypeAndTime(List<TagGroupSelect> tagGroupSelects, DelayEventTagRequest delayCreateEventTagRequest) {
//         List<TagGroupSelect> date = tagGroupSelects.stream().filter(t -> t.getAttributeCode() != null && t.getAttributeCode().equals("date")).collect(Collectors.toList());
//         if (CollectionUtil.isNotEmpty(date)) {
//             TagGroupSelect tagGroupSelectDate = date.get(0);
//             String timeValue = (String) tagGroupSelectDate.getValue();
//             List<DateEnum> dates = new ArrayList<>(Arrays.asList(DateEnum.values()));
//             List<String> dateCodes = dates.stream().map(DateEnum::getCode).collect(Collectors.toList());
//             //判断时间
//             if (dateCodes.contains(timeValue)) {
//                 timeValue = TagDateUtil.getTime((String) timeValue);
//             }
//             delayCreateEventTagRequest.setTime(timeValue);
//         }
//         List<String> attributeCodes = tagGroupSelects.stream().filter(t -> StrUtil.isNotBlank(t.getAttributeCode())).map(TagGroupSelect::getAttributeCode).collect(Collectors.toList());
//
//         if (attributeCodes.contains("view")) {
//             delayCreateEventTagRequest.setDoType(3);
//         } else if (attributeCodes.contains("addCart")) {
//             delayCreateEventTagRequest.setDoType(2);
//         } else if (attributeCodes.contains("collect")) {
//             delayCreateEventTagRequest.setDoType(4);
//         } else if (attributeCodes.contains("pay")) {
//             delayCreateEventTagRequest.setDoType(1);
//         } else if (attributeCodes.contains("pv")) {
//             delayCreateEventTagRequest.setDoType(5);
//         }
//     }
//
//     @Resource
//     private GpProductMapper gpProductMapper;
//
//     /**
//      * mq延迟创建标签
//      * 条件 有条件组装数据
//      * 数据 有数据直接插入
//      * 组ID
//      */
//     @Override
//     public void delayCreateEventTag(DelayEventTagRequest delayCreateEventTagRequest) {
//         //如果tags 不为空，直接插入
//         CreateEventTagGroupRequest request = delayCreateEventTagRequest.getCreateRequest();
//         List<TagGroupSelect> calLogicNotEvent = delayCreateEventTagRequest.getCalLogicNotEvent();
//         TagGroup tagGroup = delayCreateEventTagRequest.getTagGroup();
//         List<TagHierarchyRequest> tags = delayCreateEventTagRequest.getTags();
//         String linkApi = delayCreateEventTagRequest.getApi();
//         TagGroupSelect tagGroupSelect = delayCreateEventTagRequest.getTagGroupSelect();
//         Integer objType = delayCreateEventTagRequest.getObjType();
//         String time = delayCreateEventTagRequest.getTime();
//         Integer doType = delayCreateEventTagRequest.getDoType();
//         Integer globalTagType = delayCreateEventTagRequest.getGlobalTagType();
//         log.info("delayCreateEventTag globalTagType: {}", globalTagType);
//         if (CollectionUtil.isNotEmpty(tags)) {
//             dealTags(request, calLogicNotEvent, tagGroup, tags, objType);
//         } else {
//             //如果tags 为空，根据条件分页查询
//             //组装出数据
//             if (StrUtil.isNotBlank(linkApi)) {
//                 JSONObject linkApiJson = JSONObject.parseObject(linkApi);
//                 String apiUrl = linkApiJson.getString("url");
//                 if (StrUtil.isNotBlank(apiUrl)) {
//                     String[] split = apiUrl.split("/");
//                     String api = split[split.length - 1];
//
//                     if ("getproduct".equals(api)) {
//                         List<String> payProduct = new ArrayList<>();
//                         if (doType == 1) {
//                             payProduct = gpProductMapper.getPayProduct(time);
//                         } else if (doType == 2) {
//                             payProduct = gpProductMapper.getAddCartProduct(time);
//                         } else if (doType == 3) {
//                             payProduct = gpProductMapper.getViewProduct(time);
//                         } else if (doType == 4) {
//                             payProduct = gpProductMapper.getAddCartProduct(time);
//                         } else if (doType == 5) {
//                             payProduct = gpProductMapper.getViewProduct(time);
//                         }
//                         if (CollectionUtil.isNotEmpty(payProduct)) {
//                             List<List<String>> splitList = CollUtil.split(payProduct, 800);
//                             List<TagHierarchyRequest> tagsData;
//                             for (List<String> codes : splitList) {
//                                 List<ProductResponse> data = erpProductService.getProductListByCodes(codes);
//                                 if (CollectionUtil.isNotEmpty(data)) {
//                                     tagsData = new ArrayList<>();
//                                     //赋值页索引
//                                     if (CollectionUtil.isNotEmpty(data)) {
//                                         for (ProductResponse inData : data) {
//                                             //数据
//                                             String attributeValue = inData.getProductCode();
//                                             String attributeName = inData.getProductName();
//                                             dealLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName);
//                                         }
//                                         dealTags(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                     }
//                                 }
//                             }
//                         }
//                     } else if ("getbrand".equals(api)) {
//                         //调用方法，构造查询参数
//                         BrandRequest brandRequest = new BrandRequest();
//                         //第一页，每次查询1000
//                         brandRequest.setPageIndex(1);
//                         brandRequest.setPageSize(1000);
//                         String channelTypeCode = Optional.ofNullable(linkApiJson.getJSONObject("apiParma")).map(j -> j.getString("channelTypeCode")).orElse(null);
//                         brandRequest.setChannelTypeCode(channelTypeCode);
//                         PageResponse<BrandResponse> brandByPage = erpProductService.getBrandByPage(brandRequest);
//                         if (CollectionUtil.isNotEmpty(brandByPage.getData())) {
//                             List<TagHierarchyRequest> tagsData;
//                             int dealCount = 1000;
//                             int timeCount = BigDecimal.valueOf(brandByPage.getTotal()).divide(BigDecimal.valueOf(dealCount), 0, RoundingMode.UP).intValue();
//                             //页数大于1
//                             if (timeCount > 1) {
//                                 //循环页索引,从第二页开始
//                                 for (int i = 2; i <= timeCount; i++) {
//                                     tagsData = new ArrayList<>();
//                                     //赋值页索引
//                                     brandRequest.setPageIndex(i);
//                                     PageResponse<BrandResponse> brandByPageLimit = erpProductService.getBrandByPage(brandRequest);
//
//                                     List<BrandResponse> data = brandByPageLimit.getData();
//                                     if (CollectionUtil.isNotEmpty(data)) {
//                                         for (BrandResponse inData : data) {
//                                             //数据
//                                             String attributeValue = inData.getBrandId();
//                                             String attributeName = inData.getBrandName();
//                                             dealLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName);
//                                         }
//                                         dealTags(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                     }
//                                 }
//                             } else {
//                                 List<BrandResponse> data = brandByPage.getData();
//                                 if (CollectionUtil.isNotEmpty(data)) {
//                                     tagsData = new ArrayList<>();
//                                     //组装
//                                     for (BrandResponse inData : data) {
//                                         //数据
//                                         String attributeValue = inData.getBrandId();
//                                         String attributeName = inData.getBrandName();
//                                         dealLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName);
//                                     }
//                                     //处理标签
//                                     dealTags(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                 }
//                             }
//                         }
//                     } else if ("getdegree".equals(api)) {
//                         List<DegreeResponse> data = erpProductService.getDegree(new DegreeRequest());
//
//                         if (CollectionUtil.isNotEmpty(data)) {
//                             List<TagHierarchyRequest> tagsData = new ArrayList<>();
//                             for (DegreeResponse inData : data) {
//                                 //数据
//                                 String attributeValue = inData.getDegreeCode();
//                                 String attributeName = inData.getDegreeName();
//                                 dealLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName);
//                             }
//                             dealTags(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                         }
//                     }
//                 }
//             }
//         }
//         //最后发送mq
//         //发mq
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             rabbitTemplate.convertAndSend(VIP_TAG_QUEUE, String.valueOf(tagGroup.getId()));
//         } else {
//             rabbitTemplate.convertAndSend(TAG_QUEUE, String.valueOf(tagGroup.getId()));
//         }
//     }
//
//     /**
//      * 组装分层
//      *
//      * @param request
//      * @param tagGroupSelect
//      * @param tagsData
//      * @param attributeValue
//      * @param attributeName
//      */
//     private void dealLayer(CreateEventTagGroupRequest request, TagGroupSelect tagGroupSelect, List<TagHierarchyRequest> tagsData, Object attributeValue, String attributeName) {
//         //组装分层信息
//         TagHierarchyRequest tagHierarchyRequest = new TagHierarchyRequest();
//         tagHierarchyRequest.setName(attributeName);
//         TagGroupExpressionRequest tagGroupExpressionRequest = new TagGroupExpressionRequest();
//         tagGroupExpressionRequest.setChannelLevel(request.getChannelLevel());
//         tagGroupExpressionRequest.setChannelCode(request.getChannelCode());
//         tagGroupExpressionRequest.setType("tagGroup");
//         tagGroupExpressionRequest.setTagGroupSource(2);
//         Object[] selectValue = new Object[]{attributeValue};
//         tagGroupSelect.setValue(selectValue);
//         tagGroupExpressionRequest.setSelects(Collections.singletonList(tagGroupSelect));
//         tagHierarchyRequest.setCalLogic(Collections.singletonList(tagGroupExpressionRequest));
//         TagHierarchyRequest tagHierarchyRequestCopy = JSON.parseObject(JSON.toJSONString(tagHierarchyRequest), TagHierarchyRequest.class);
//         tagsData.add(tagHierarchyRequestCopy);
//     }
//
//
//     /**
//      * 处理分层
//      *
//      * @param request
//      * @param calLogicNotEvent
//      * @param tagGroup
//      * @param tags
//      */
//     private void dealTags(CreateEventTagGroupRequest request, List<TagGroupSelect> calLogicNotEvent, TagGroup tagGroup, List<TagHierarchyRequest> tags, Integer objType) {
//         List<TagGroupUseRelation> relationList = new ArrayList<>();
//         Integer globalTagType = request.getGlobalTagType();
//         Tag tag;
//         for (TagHierarchyRequest tagHierarchyRequest : tags) {
//             List<TagGroupSelect> selectList = tagHierarchyRequest.getCalLogic().get(0).getSelects();
//             List<TagGroupSelect> list = new ArrayList<>();
//             list.addAll(calLogicNotEvent);
//             list.addAll(selectList);
//             TagGroupExpressionRequest tagGroupExpressionRequest = tagHierarchyRequest.getCalLogic().get(0);
//             tagGroupExpressionRequest.setSelects(list);
//             // 设置全局标签属性
//             tagHierarchyRequest.getCalLogic().forEach(logic -> logic.setGlobalTagType(globalTagType));
//             String sql = buildQueryAllChannel(tagHierarchyRequest.getCalLogic(), tagGroup.getDataRange());
//             if (StrUtil.isBlank(sql)) {
//                 throw new Http400Exception("layerExpressionGenerateError", "公式生成错误");
//             }
//             Object obj = Optional.ofNullable(selectList.get(0)).map(TagGroupSelect::getValue).map(t -> ((JSONArray) t).get(0)).orElse(null);
//             tag = new Tag();
//             tag.setTagName(tagHierarchyRequest.getName());
//             tag.setDescribe(tagHierarchyRequest.getDescription());
//             tag.setTagGroupId(tagGroup.getId());
//             tag.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//             tag.setSqlQuery(sql);
//             tag.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//             tag.setLayerCount(0);
//             tag.setChannelCode(request.getChannelCode());
//             tag.setChannelLevel(request.getChannelLevel());
//             tag.setCountUpdateTime(new Date());
//             tag.setSort(0);
//             tag.setCreateTime(new Date());
//             tag.setCreater(request.getUserId());
//             tag.setCreaterName(request.getUserName());
//             tag.setIsDeleted(0);
//             tag.setTagObjId(String.valueOf(obj));
//             tag.setTagObjType(objType);
//             //分层循环新增,需要返回的主键id做后续操作
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagCustomVipMapper.insertTag(tag);
//                 try {
//                     // 级联更新的编辑标志
//                     redisTemplate.opsForValue().set(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                 } catch (Exception e) {
//                     log.error("set redis fail:tagId:" + tag.getId(), e);
//                     redisTemplate.delete(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId());
//                 }
//             } else {
//                 tagCustomMapper.insertTag(tag);
//                 try {
//                     // 级联更新的编辑标志
//                     redisTemplate.opsForValue().set(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                 } catch (Exception e) {
//                     log.error("set redis fail:tagId:" + tag.getId(), e);
//                     redisTemplate.delete(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId());
//                 }
//             }
//             //处理引用关系
//             List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//             for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                 if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                     List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                     TagGroupSelect select = selects.get(0);
//                     Object value = select.getValue();
//                     if (ObjectUtil.isNotNull(value)) {
//                         List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                         TagGroupUseRelation relation;
//                         for (Integer id : listValue) {
//                             relation = new TagGroupUseRelation();
//                             relation.setTagId(tag.getId());
//                             relation.setTagGroupId(tagGroup.getId());
//                             relation.setUseTagId(id);
//                             relation.setUseTagGroupId(tagGroupRequest.getTagGroupId());
//                             relation.setIsDeleted(0);
//                             relationList.add(relation);
//                         }
//                     }
//                 }
//             }
//         }
//         //插入引用
//         if (CollectionUtil.isNotEmpty(relationList)) {
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationList);
//             } else {
//                 tagGroupRelationCustomMapper.insertTagGroupRelation(relationList);
//             }
//         }
//         TagGroupLog tagGroupLog = new TagGroupLog();
//         tagGroupLog.setTagGroupId(tagGroup.getId());
//         tagGroupLog.setActionType(TagGroupActionEnum.CREATE.getAction());
//         tagGroupLog.setActionTime(new Date());
//         tagGroupLog.setCreater(request.getUserId());
//         tagGroupLog.setCreaterName(request.getUserName());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupLogVipMapper.insertLog(tagGroupLog);
//         } else {
//             tagGroupLogMapper.insertLog(tagGroupLog);
//         }
//     }
//
//
//     @Override
//     public TagGroupDetailsResponse getTagGroupDetail(TagGroupDetailRequest request) {
//         TagGroupDetailsResponse response = new TagGroupDetailsResponse();
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("getTagGroupDetail globalTagType: {}, tagGroupId: {}", globalTagType, request.getTagGroupId());
//         TagGroup tagGroup;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroup = tagGroupCustomVipMapper.getTagGroupById(request.getTagGroupId());
//         } else {
//             tagGroup = tagGroupCustomMapper.getTagGroupById(request.getTagGroupId());
//         }
//         if (ObjectUtil.isNull(tagGroup)) {
//             throw new Http400Exception("tagGroupNotFound", "标签组不存在");
//         }
//         response.setCreateWay(tagGroup.getCreateWay());
//         response.setCustomType(tagGroup.getCustomType());
//         response.setTagGroupName(tagGroup.getTagGroupName());
//         response.setTagGroupCateId(tagGroup.getTagGroupCateId());
//         response.setRemark(tagGroup.getRemark());
//         response.setCalLogic(JSON.parseArray(tagGroup.getExpression(), TagGroupLogicResponse.class));
//         response.setCreater(tagGroup.getCreater());
//         response.setCreaterName(tagGroup.getCreaterName());
//         response.setCreateTime(tagGroup.getCreateTime().getTime());
//         response.setDataRange(tagGroup.getDataRange());
//         List<TagGroupChannel> channel;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             channel = tagGroupCustomVipMapper.getTagGroupChannelByTagGroupId(tagGroup.getId());
//         } else {
//             channel = tagGroupCustomMapper.getTagGroupChannelByTagGroupId(tagGroup.getId());
//         }
//         if (CollectionUtil.isNotEmpty(channel)) {
//             TagGroupChannel tagGroupChannel = channel.get(0);
//             response.setChannelLevel(tagGroupChannel.getChannelLevel());
//             response.setChannelCode(tagGroupChannel.getChannelCode());
//         }
//         Integer isNotLimit = tagGroup.getIsNotLimit();
//         if (ObjectUtil.isNotNull(isNotLimit) && isNotLimit == 1) {
//             response.setTags(new ArrayList<>());
//         } else {
//             List<Tag> tags;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tags = tagCustomVipMapper.getByTagGroupId(request.getTagGroupId());
//             } else {
//                 tags = tagCustomMapper.getByTagGroupId(request.getTagGroupId());
//             }
//             List<TagHierarchyResponse> calLogicResponses = new ArrayList<>();
//             if (CollectionUtil.isNotEmpty(tags)) {
//                 TagHierarchyResponse hierarchyResponse = null;
//                 for (Tag tag : tags) {
//                     hierarchyResponse = new TagHierarchyResponse();
//                     hierarchyResponse.setName(tag.getTagName());
//                     hierarchyResponse.setLayerCount(tag.getLayerCount());
//                     hierarchyResponse.setTagId(tag.getId());
//                     hierarchyResponse.setDescription(tag.getDescribe());
//                     hierarchyResponse.setSort(tag.getSort());
//                     String expression = tag.getExpression();
//                     if (StrUtil.isNotBlank(expression)) {
//                         List<TagGroupLogicResponse> tagGroupResponses = JSON.parseArray(expression, TagGroupLogicResponse.class);
//                         hierarchyResponse.setCalLogic(tagGroupResponses);
//                     }
//                     calLogicResponses.add(hierarchyResponse);
//                 }
//             }
//             response.setTags(calLogicResponses);
//         }
//         Integer tagGroupRelationCount;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupRelationCount = tagCustomVipMapper.getTagGroupRelationCount(tagGroup.getId());
//         } else {
//             tagGroupRelationCount = tagCustomMapper.getTagGroupRelationCount(tagGroup.getId());
//         }
//         //查询是否被引用
//         response.setTagGroupIsUsed(tagGroupRelationCount > 0 ? 1 : 0);
//         return response;
//     }
//
//
//     @Override
//     public TagGroupDetailsResponse getEventTagGroupDetail(TagGroupDetailRequest request) {
//         return getTagGroupDetail(request);
//     }
//
//
//     @Override
//     @Transactional(rollbackFor = Exception.class)
//     public CommonResponse editTagGroup(EditTagGroupRequest request) {
//         Integer tagGroupId = request.getTagGroupId();
//         //判断名称是否重复
//         TagGroupNameRequest tagGroupNameRequest = new TagGroupNameRequest();
//         tagGroupNameRequest.setTagGroupName(request.getTagGroupName());
//         tagGroupNameRequest.setChannelCode(request.getChannelCode());
//         tagGroupNameRequest.setTagGroupId(tagGroupId);
//         int i;
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("editTagGroup globalTagType: {}", globalTagType);
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             i = tagGroupCustomVipMapper.getTagGroupByName(tagGroupNameRequest);
//         } else {
//             i = tagGroupCustomMapper.getTagGroupByName(tagGroupNameRequest);
//         }
//         if (i > 0) {
//             throw new Http400Exception("duplicateName", "名称重复");
//         }
//         //判断计算状态=====>
//         TagGroup tagGroupById;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupById = tagGroupCustomVipMapper.getTagGroupById(tagGroupId);
//         } else {
//             tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//         }
//         Integer dealStatus = tagGroupById.getDealStatus();
//         if (dealStatus.equals(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus())) {
//             throw new Http400Exception("tagGroupIsCalculated", "标签组正在计算中,请稍后编辑");
//         }
//         List<TagGroupUseRelation> relationList = new ArrayList<>();
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(request.getTagGroupId());
//         tagGroup.setTagGroupName(request.getTagGroupName());
//         tagGroup.setTagGroupCateId(request.getTagGroupCateId());
//         tagGroup.setTagGroupSource(1);
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroup.setExpression(JSONObject.toJSONString(request.getTags()));
//         tagGroup.setCreateWay(request.getCreateWay());
//         tagGroup.setCustomType(request.getCustomType());
//         tagGroup.setIsNotLimit(0);
//         tagGroup.setEventParam(StrUtil.EMPTY);
//         tagGroup.setRemark(request.getRemark());
//         tagGroup.setUpdater(request.getUserId());
//         tagGroup.setUpdaterName(request.getUserName());
//         tagGroup.setUpdateTime(new Date());
//         tagGroup.setDataRange(request.getDataRange());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//         } else {
//             tagGroupCustomMapper.updateTagGroupById(tagGroup);
//         }
//         List<TagHierarchyRequest> tags = request.getTags();
//         if (CollectionUtil.isEmpty(tags)) {
//             throw new Http400Exception("pleaseAddLayer", "请添加分层");
//         }
//         for (TagHierarchyRequest tagHierarchyRequest : tags) {
//             if (CollectionUtil.isEmpty(tagHierarchyRequest.getCalLogic())) {
//                 throw new Http400Exception("pleaseAddLayerCalLogic", "请添加分层计算逻辑");
//             }
//         }
//         //查询原有分层
//         List<Tag> tagList;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagList = tagCustomVipMapper.getByTagGroupId(tagGroupId);
//         } else {
//             tagList = tagCustomMapper.getByTagGroupId(tagGroupId);
//         }
//         //现有分层（编辑过的）
//         Map<Integer, List<TagHierarchyRequest>> tagHierarchyMap = tags.stream().filter(t -> ObjectUtil.isNotNull(t.getTagId())).collect(Collectors.groupingBy(TagHierarchyRequest::getTagId));
//         //遍历原分层
//         List<Integer> deleteTags = new ArrayList<>();
//         Tag tag;
//         for (Tag inTag : tagList) {
//             List<TagHierarchyRequest> tagHierarchyRequestList = tagHierarchyMap.get(inTag.getId());
//             //比对分层是否已经被删除
//             if (CollectionUtil.isNotEmpty(tagHierarchyRequestList)) {
//                 TagHierarchyRequest tagHierarchyRequest = tagHierarchyRequestList.get(0);
//                 Integer tagId = tagHierarchyRequest.getTagId();
//                 List<TagGroupExpressionRequest> calLogic = tagHierarchyRequest.getCalLogic();
//                 // 设置全局标签类型属性
//                 calLogic.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                 boolean b = checkEx(calLogic);
//                 if (!b) {
//                     throw new Http400Exception("layerExpressionIllegal", "分层[" + tagHierarchyRequest.getName() + "]公式不合法");
//                 }
//                 String sql = buildQueryAllChannel(calLogic, tagGroup.getDataRange());
//                 if (StrUtil.isBlank(sql)) {
//                     throw new Http400Exception("layerExpressionGenerateError", "分层[" + tagHierarchyRequest.getName() + "]公式生成错误");
//                 }
//                 //判断分层内关系， 更新分层关系
//                 List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//                 for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                     if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                         List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                         Integer groupId = tagGroupRequest.getTagGroupId();
//                         if (groupId.equals(tagGroupId)) {
//                             throw new Http400Exception("selfError", "无法引用自身");
//                         }
//                         TagGroupSelect select = selects.get(0);
//                         Object value = select.getValue();
//                         if (ObjectUtil.isNotNull(value)) {
//                             TagGroupUseRelation relation;
//                             List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                             for (Integer id : listValue) {
//                                 relation = new TagGroupUseRelation();
//                                 relation.setTagId(tagId);
//                                 relation.setTagGroupId(request.getTagGroupId());
//                                 relation.setUseTagId(id);
//                                 relation.setUseTagGroupId(groupId);
//                                 relation.setIsDeleted(0);
//                                 relationList.add(relation);
//                             }
//                         }
//                     }
//                 }
//                 //更新分层
//                 tag = new Tag();
//                 tag.setId(tagId);
//                 tag.setTagName(tagHierarchyRequest.getName());
//                 tag.setDescribe(tagHierarchyRequest.getDescription());
//                 tag.setTagGroupId(tagGroup.getId());
//                 tag.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//                 tag.setSqlQuery(sql);
//                 tag.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//                 tag.setChannelLevel(request.getChannelLevel());
//                 tag.setLayerCount(tagHierarchyRequest.getLayerCount());
//                 tag.setSort(tagHierarchyRequest.getSort());
//                 tag.setUpdateTime(new Date());
//                 tag.setUpdater(request.getUserId());
//                 tag.setCreaterName(request.getUserName());
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.updateTagById(tag);
//                 } else {
//                     tagCustomMapper.updateTagById(tag);
//                 }
//                 try {
//                     // 级联更新的编辑标志
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         redisTemplate.opsForValue().set(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                     } else {
//                         redisTemplate.opsForValue().set(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                     }
//                 } catch (Exception e) {
//                     log.error("set redis fail, globalTagType: {}, tagId: {}, error: {}", globalTagType, tag.getId(), ExceptionUtils.getStackTrace(e));
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         redisTemplate.delete(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId());
//                     } else {
//                         redisTemplate.delete(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId());
//                     }
//                 }
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     //删除之前的引用
//                     // 会员标签
//                     tagGroupRelationCustomVipMapper.deleteRelationByTagId(tagId);
//                     //添加新的引用
//                     if (CollectionUtil.isNotEmpty(relationList)) {
//                         tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationList);
//                     }
//                 } else {
//                     //删除之前的引用
//                     tagGroupRelationCustomMapper.deleteRelationByTagId(tagId);
//                     //添加新的引用
//                     if (CollectionUtil.isNotEmpty(relationList)) {
//                         tagGroupRelationCustomMapper.insertTagGroupRelation(relationList);
//                     }
//                 }
//             } else {
//                 // 查询分层是否有被引用
//                 TagRelationInfo tagRelationInfo;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagRelationInfo = tagRelationInfoVipDao.queryByIdType(Long.valueOf(inTag.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 } else {
//                     tagRelationInfo = tagRelationInfoDao.queryByIdType(Long.valueOf(inTag.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 }
//                 if (null != tagRelationInfo) {
//                     if (!CollectionUtils.isEmpty(tagRelationInfo.getPassiveCrowdIds()) || !CollectionUtils.isEmpty(tagRelationInfo.getPassiveTagIds())) {
//                         throw new Http400Exception("delete tag fail!", "分层被标签或者人群引用");
//                     }
//                 }
//                 //删除标签，及引用关系
//                 Tag tagDel = new Tag();
//                 tagDel.setId(inTag.getId());
//                 tagDel.setIsDeleted(1);
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.updateTagById(tagDel);
//                     tagGroupRelationCustomVipMapper.deleteRelationByTagId(inTag.getId());
//                 } else {
//                     tagCustomMapper.updateTagById(tagDel);
//                     tagGroupRelationCustomMapper.deleteRelationByTagId(inTag.getId());
//                 }
//                 deleteTags.add(inTag.getId());
//             }
//         }
//         if (CollectionUtil.isNotEmpty(deleteTags)) {
//             //更新引用关系
//             List<TagRelationInfo> tagRelationInfos;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagRelationInfos = tagRelationInfoVipDao.batchQueryByIdTypes(deleteTags, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//             } else {
//                 tagRelationInfos = tagRelationInfoDao.batchQueryByIdTypes(deleteTags, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//             }
//             updateTagRelation(deleteTags, tagRelationInfos, globalTagType);
//         }
//         //不带tagId,新增的分层
//         List<TagHierarchyRequest> addHierarchy = tags.stream().filter(t -> ObjectUtil.isNull(t.getTagId())).collect(Collectors.toList());
//         if (CollectionUtil.isNotEmpty(addHierarchy)) {
//             //新增
//             List<TagGroupUseRelation> relationAddList = new ArrayList<>();
//             for (TagHierarchyRequest tagHierarchyRequest : addHierarchy) {
//                 List<TagGroupExpressionRequest> calLogic = tagHierarchyRequest.getCalLogic();
//                 // 设置全局标签类型属性
//                 calLogic.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                 boolean b = checkEx(calLogic);
//                 if (!b) {
//                     throw new Http400Exception("layerExpressionIllegal", "分层[" + tagHierarchyRequest.getName() + "]公式不合法");
//                 }
//                 String sql = buildQueryAllChannel(calLogic, tagGroup.getDataRange());
//                 if (StrUtil.isBlank(sql)) {
//                     throw new Http400Exception("layerExpressionGenerateError", "分层[" + tagHierarchyRequest.getName() + "]公式生成错误");
//                 }
//                 Tag tagAdd = new Tag();
//                 tagAdd.setTagName(tagHierarchyRequest.getName());
//                 tagAdd.setDescribe(tagHierarchyRequest.getDescription());
//                 tagAdd.setTagGroupId(tagGroup.getId());
//                 tagAdd.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//                 tagAdd.setSqlQuery(sql);
//                 tagAdd.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//                 tagAdd.setLayerCount(tagHierarchyRequest.getLayerCount());
//                 tagAdd.setCountUpdateTime(new Date());
//                 tagAdd.setChannelLevel(request.getChannelLevel());
//                 tagAdd.setLayerCount(tagHierarchyRequest.getLayerCount());
//                 tagAdd.setSort(tagHierarchyRequest.getSort());
//                 tagAdd.setCreateTime(new Date());
//                 tagAdd.setCreater(request.getUserId());
//                 tagAdd.setCreaterName(request.getUserName());
//                 tagAdd.setIsDeleted(0);
//                 //分层循环新增,需要返回的主键id做后续操作
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.insertTag(tagAdd);
//                 } else {
//                     tagCustomMapper.insertTag(tagAdd);
//                 }
//                 List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//                 TagGroupUseRelation relation = new TagGroupUseRelation();
//                 for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                     if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                         List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                         TagGroupSelect select = selects.get(0);
//                         Object value = select.getValue();
//                         if (ObjectUtil.isNotNull(value)) {
//                             List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                             for (Integer id : listValue) {
//                                 relation.setTagId(tagAdd.getId());
//                                 relation.setTagGroupId(tagGroup.getId());
//                                 relation.setUseTagId(id);
//                                 relation.setUseTagGroupId(tagGroupRequest.getTagGroupId());
//                                 relation.setIsDeleted(0);
//                                 relationAddList.add(relation);
//                             }
//                         }
//                     }
//                 }
//             }
//             //插入引用
//             if (CollectionUtil.isNotEmpty(relationAddList)) {
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationAddList);
//                 } else {
//                     tagGroupRelationCustomMapper.insertTagGroupRelation(relationAddList);
//                 }
//             }
//         }
//         //查询被哪些其他标签组引用,更新这些标签组
//         List<TagGroupUseRelation> tagGroupBeUsedRelations;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupBeUsedRelations = tagGroupRelationCustomVipMapper.getTagGroupBeUsedRelation(tagGroupId);
//         } else {
//             tagGroupBeUsedRelations = tagGroupRelationCustomMapper.getTagGroupBeUsedRelation(tagGroupId);
//         }
//         if (CollectionUtil.isNotEmpty(tagGroupBeUsedRelations)) {
//             for (TagGroupUseRelation relation : tagGroupBeUsedRelations) {
//                 //更新被引用标签
//                 Integer relationTagGroupId = relation.getTagGroupId();
//                 List<Tag> byTagGroupId;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     byTagGroupId = tagCustomVipMapper.getByTagGroupId(relationTagGroupId);
//                 } else {
//                     byTagGroupId = tagCustomMapper.getByTagGroupId(relationTagGroupId);
//                 }
//                 if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                     for (Tag inTag : byTagGroupId) {
//                         String expression = inTag.getExpression();
//                         List<TagGroupExpressionRequest> requests = JSON.parseArray(expression, TagGroupExpressionRequest.class);
//                         // 设置全局标签类型属性
//                         requests.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                         String newSql = buildQueryAllChannel(requests, tagGroup.getDataRange());
//                         inTag.setSqlQuery(newSql);
//                         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                             // 会员标签
//                             tagCustomVipMapper.updateTagById(inTag);
//                         } else {
//                             tagCustomMapper.updateTagById(inTag);
//                         }
//                     }
//                 }
//             }
//         }
//         TagGroupLog tagGroupLog = new TagGroupLog();
//         tagGroupLog.setTagGroupId(tagGroup.getId());
//         tagGroupLog.setActionType(TagGroupActionEnum.UPDATE.getAction());
//         tagGroupLog.setActionTime(new Date());
//         tagGroupLog.setCreater(request.getUserId());
//         tagGroupLog.setCreaterName(request.getUserName());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupLogVipMapper.insertLog(tagGroupLog);
//             //发mq
//             sendMsg(VIP_TAG_QUEUE, request.getTagGroupId());
//         } else {
//             tagGroupLogMapper.insertLog(tagGroupLog);
//             //发mq
//             sendMsg(TAG_QUEUE, request.getTagGroupId());
//         }
//         // 删除标签维度表中，该标签组下面的所有的一级业务渠道，平台，店铺维度的标签
//         if (!Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             tagDimensionMapper.deleteDimensionTagByTagGroupId(request.getTagGroupId(), request.getUserId(), request.getUserName());
//         }
//         return new CommonResponse(true);
//     }
//
//
//     @Override
//     @Transactional(rollbackFor = Exception.class)
//     public CommonResponse editEventTagGroup(EditEventTagGroupRequest request) {
//         Integer tagGroupId = request.getTagGroupId();
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("editEventTagGroup tagGroupId: {}, globalTagType: {}", tagGroupId, globalTagType);
//         if (request.getIsSync() == null || request.getIsSync() == 0) {
//             //判断是否被引用，如果被引用无法被编辑
//             Integer tagGroupRelationCount;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupRelationCount = tagCustomVipMapper.getTagGroupRelationCount(tagGroupId);
//             } else {
//                 tagGroupRelationCount = tagCustomMapper.getTagGroupRelationCount(tagGroupId);
//             }
//             if (tagGroupRelationCount > 0) {
//                 throw new Http400Exception("event tag is used", "标签组已经被引用，无法编辑");
//             }
//         }
//         //判断名称是否重复
//         TagGroupNameRequest tagGroupNameRequest = new TagGroupNameRequest();
//         tagGroupNameRequest.setTagGroupName(request.getTagGroupName());
//         tagGroupNameRequest.setChannelCode(request.getChannelCode());
//         tagGroupNameRequest.setTagGroupId(tagGroupId);
//         int i;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             i = tagGroupCustomVipMapper.getTagGroupByName(tagGroupNameRequest);
//         } else {
//             i = tagGroupCustomMapper.getTagGroupByName(tagGroupNameRequest);
//         }
//         if (i > 0) {
//             throw new Http400Exception("duplicateName", "名称重复");
//         }
//         //判断计算状态=====>
//         TagGroup tagGroupById;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupById = tagGroupCustomVipMapper.getTagGroupById(tagGroupId);
//         } else {
//             tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//         }
//         Integer dealStatus = tagGroupById.getDealStatus();
//         if (dealStatus.equals(TagGroupDelStatusEnum.IN_THE_CREATE.getDealStatus())) {
//             throw new Http400Exception("tagGroupIsCalculated", "标签组正在生成中,请稍后编辑");
//         }
//         if (dealStatus.equals(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus())) {
//             throw new Http400Exception("tagGroupIsCalculated", "标签组正在计算中,请稍后编辑");
//         }
//         List<TagGroupExpressionRequest> calLogics = request.getCalLogic();
//         boolean isTrue = checkEx(calLogics);
//         if (!isTrue) {
//             throw new Http400Exception("eventExpressionIllegal", "公式不合法");
//         }
//         if (CollectionUtil.isEmpty(calLogics)) {
//             throw new Http400Exception("paramError", "参数错误");
//         }
//         //获取
//         TagGroupExpressionRequest expressionRequest = calLogics.get(0);
//         //偏好单个公式
//         List<TagGroupSelect> tagGroupSelects = expressionRequest.getSelects();
//
//         if (CollectionUtil.isEmpty(tagGroupSelects)) {
//             throw new Http400Exception("pleaseChoose", "请选择筛选项");
//         }
//         //处理内部分层
//         List<TagGroupSelect> calLogicEvent = tagGroupSelects.stream().filter(c -> ObjectUtil.isNotNull(c.getIsEventObject()) && c.getIsEventObject() == 1).collect(Collectors.toList());
//         if (CollectionUtil.isEmpty(calLogicEvent)) {
//             throw new Http400Exception("pleaseAddEventObject", "请选择行为对象");
//         }
//         List<TagGroupSelect> calLogicNotEvent = tagGroupSelects.stream().filter(c -> ObjectUtil.isNull(c.getIsEventObject()) || c.getIsEventObject() == 0).collect(Collectors.toList());
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(request.getTagGroupId());
//         tagGroup.setTagGroupName(request.getTagGroupName());
//         tagGroup.setTagGroupCateId(request.getTagGroupCateId());
//         tagGroup.setExpression(JSON.toJSONString(request.getCalLogic()));
//         tagGroup.setTagGroupSource(1);
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroup.setCreateWay(request.getCreateWay());
//         tagGroup.setCustomType(request.getCustomType());
//         tagGroup.setRemark(request.getRemark());
//         tagGroup.setUpdater(request.getUserId());
//         tagGroup.setUpdaterName(request.getUserName());
//         tagGroup.setUpdateTime(new Date());
//         tagGroup.setDataRange(request.getDataRange());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//         } else {
//             tagGroupCustomMapper.updateTagGroupById(tagGroup);
//         }
//         List<TagHierarchyRequest> tags = request.getTags();
//         //如果为空说明是不限
//         TagGroupSelect tagGroupSelect = calLogicEvent.get(0);
//         List<Object> value = (List<Object>) tagGroupSelect.getValue();
//         Integer attributeId = (Integer) tagGroupSelect.getAttributeId();
//         if (ObjectUtil.isNull(attributeId)) {
//             throw new Http400Exception("attributeId is null", "数据有误");
//         }
//         //根据id获取
//         AttributeConf attributeConf;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             attributeConf = attributeConfCustomVipMapper.getConfigById(attributeId);
//         } else {
//             attributeConf = attributeConfCustomMapper.getConfigById(attributeId);
//         }
//         if (ObjectUtil.isNull(attributeConf)) {
//             throw new Http400Exception("attributeConf is null", "数据有误");
//         }
//         String linkApi = attributeConf.getLinkApi();
//         if (StrUtil.isBlank(linkApi)) {
//             throw new Http400Exception("", "数据有误");
//         }
//         String attributeCode = attributeConf.getAttributeCode();
//         if (StrUtil.isBlank(attributeCode)) {
//             throw new Http400Exception("attributeCode is null", "attributeCode为空");
//         }
//         Integer objType = null;
//         if ("cate".equals(attributeCode)) {
//             objType = 2;
//         } else if ("brand".equals(attributeCode)) {
//             objType = 1;
//         } else if ("product".equals(attributeCode)) {
//             objType = 0;
//         } else if ("cycle".equals(attributeCode)) {
//             objType = 3;
//         } else if ("degree".equals(attributeCode)) {
//             objType = 4;
//         }
//         if (objType == null) {
//             throw new Http400Exception("objType is null", "objType为空");
//         }
//         //如果为空，不限
//         if (CollectionUtil.isEmpty(tags)) {
//             if (CollectionUtil.isNotEmpty(value)) {
//                 throw new Http400Exception("", "数据有误");
//             }
//             tagGroup.setIsNotLimit(1);
//             tagGroup.setEventParam(JSON.toJSONString(request));
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//                 tagCustomVipMapper.deleteNotUse(tagGroupId);
//             } else {
//                 tagGroupCustomMapper.updateTagGroupById(tagGroup);
//                 //TODO 删除没有被引用的标签
//                 tagCustomMapper.deleteNotUse(tagGroupId);
//             }
//             //判断是否从接口获取数据
//             JSONObject linkApiJson = JSONObject.parseObject(linkApi);
//             JSONArray data = linkApiJson.getJSONArray("data");
//             if (CollectionUtil.isNotEmpty(data)) {
//                 //如果是预置数据，获取data
//                 List<TagHierarchyRequest> tagsData = new ArrayList<>();
//                 //获取id，加字段
//                 List<Tag> byTagGroupId;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     byTagGroupId = tagCustomMapper.getByTagGroupId(tagGroupId);
//                 } else {
//                     byTagGroupId = tagCustomMapper.getByTagGroupId(tagGroupId);
//                 }
//                 //通过保存的事件偏好对象唯一id获取标签id
//                 Map<Object, Integer> objIdMap = byTagGroupId.stream().collect(Collectors.toMap(Tag::getTagObjId, Tag::getId, (oldValue, newValue) -> newValue));
//                 for (Object inData : data) {
//                     //数据
//                     JSONObject inDataJson = (JSONObject) inData;
//                     Object attributeValue = inDataJson.get("attributeValue");
//                     String attributeName = inDataJson.getString("attributeName");
//                     Integer tagId = objIdMap.get(attributeValue);
//                     //处理分层如果有加上id
//                     dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                 }
//                 //直接调用
//                 dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                 //发mq
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_TAG_QUEUE, String.valueOf(tagGroup.getId()));
//                 } else {
//                     sendMsg(TAG_QUEUE, String.valueOf(tagGroup.getId()));
//                 }
//             } else {
//                 //如果没有data,调用方法
//                 //调用方法需要分页调用，调用，一千一次
//                 String apiUrl = linkApiJson.getString("url");
//                 if (StrUtil.isNotBlank(apiUrl)) {
//                     //扔mq，直接返回
//                     DelayEventTagRequest delayEditEventTagRequest = new DelayEventTagRequest();
//                     delayEditEventTagRequest.setEditRequest(request);
//                     delayEditEventTagRequest.setCalLogicNotEvent(calLogicNotEvent);
//                     delayEditEventTagRequest.setTagGroup(tagGroup);
//                     delayEditEventTagRequest.setApi(linkApi);
//                     delayEditEventTagRequest.setTagGroupSelect(tagGroupSelect);
//                     delayEditEventTagRequest.setType(1);
//                     //处理时间跟动作类型
//                     doTypeAndTime(tagGroupSelects, delayEditEventTagRequest);
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         sendMsg(VIP_EVENT_TAG_QUEUE, JSON.toJSONString(delayEditEventTagRequest));
//                     } else {
//                         sendMsg(EVENT_QUEUE, JSON.toJSONString(delayEditEventTagRequest));
//                     }
//                     //发送完mq状态修改为创建中
//                     TagGroup tagGroupCopy = new TagGroup();
//                     tagGroupCopy.setId(tagGroup.getId());
//                     tagGroupCopy.setDealStatus(TagGroupDelStatusEnum.IN_THE_CREATE.getDealStatus());
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagGroupCustomVipMapper.updateTagGroupById(tagGroupCopy);
//                     } else {
//                         tagGroupCustomMapper.updateTagGroupById(tagGroupCopy);
//                     }
//                 }
//             }
//         } else {
//             tagGroup.setIsNotLimit(0);
//             tagGroup.setEventParam(JSON.toJSONString(request));
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//             } else {
//                 tagGroupCustomMapper.updateTagGroupById(tagGroup);
//             }
//             //删除除这些分层以外的数据
//             List<Integer> tagIds = tags.stream().map(TagHierarchyRequest::getTagId).collect(Collectors.toList());
//             if (CollectionUtil.isEmpty(tagIds)) {
//                 //删除这个标签组下的所有分层
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.deleteByTagGroupId(tagGroupId);
//                 } else {
//                     tagCustomMapper.deleteByTagGroupId(tagGroupId);
//                 }
//             } else {
//                 //查询这个标签组下面所有的id，取差集，分批次删除，防止太多in报错
//                 List<Tag> byTagGroupId;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     byTagGroupId = tagCustomVipMapper.getByTagGroupId(tagGroupId);
//                 } else {
//                     byTagGroupId = tagCustomMapper.getByTagGroupId(tagGroupId);
//                 }
//                 if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                     //分层不为空则删除不再需要的分层
//                     List<Integer> deleteIds = byTagGroupId.stream().filter(t -> !tagIds.contains(t.getId())).map(Tag::getId).collect(Collectors.toList());
//                     List<List<Integer>> splitIds = CollUtil.split(deleteIds, 800);
//                     for (List<Integer> inIds : splitIds) {
//                         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                             // 会员标签
//                             tagCustomVipMapper.deleteByIds(inIds);
//                         } else {
//                             tagCustomMapper.deleteByIds(inIds);
//                         }
//                     }
//                 }
//             }
//             //如果分层小于500，直接调用
//             //如果分层大于500，走mq
//             if (tags.size() > 500) {
//                 DelayEventTagRequest delayEditEventTagRequest = new DelayEventTagRequest();
//                 delayEditEventTagRequest.setEditRequest(request);
//                 delayEditEventTagRequest.setCalLogicNotEvent(calLogicNotEvent);
//                 delayEditEventTagRequest.setTagGroup(tagGroup);
//                 delayEditEventTagRequest.setType(1);
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_EVENT_TAG_QUEUE, JSON.toJSONString(delayEditEventTagRequest));
//                 } else {
//                     sendMsg(EVENT_QUEUE, JSON.toJSONString(delayEditEventTagRequest));
//                 }
//                 TagGroup tagGroupCopy = new TagGroup();
//                 tagGroupCopy.setId(tagGroup.getId());
//                 tagGroupCopy.setDealStatus(TagGroupDelStatusEnum.IN_THE_CREATE.getDealStatus());
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagGroupCustomVipMapper.updateTagGroupById(tagGroupCopy);
//                 } else {
//                     tagGroupCustomMapper.updateTagGroupById(tagGroupCopy);
//                 }
//             } else {
//                 dealEventTagEdit(request, calLogicNotEvent, tagGroup, tags, objType);
//                 //发mq
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     sendMsg(VIP_TAG_QUEUE, String.valueOf(tagGroup.getId()));
//                 } else {
//                     sendMsg(TAG_QUEUE, String.valueOf(tagGroup.getId()));
//                 }
//             }
//         }
//         return new CommonResponse(true);
//     }
//
//     /**
//      * 处理编辑时的分层
//      *
//      * @param request
//      * @param tagGroupSelect
//      * @param tagsData
//      * @param attributeValue
//      * @param attributeName
//      * @param tagId
//      */
//     private void dealEditLayer(EditEventTagGroupRequest request, TagGroupSelect tagGroupSelect, List<TagHierarchyRequest> tagsData, Object attributeValue, String attributeName, Integer tagId) {
//         //组装分层信息
//         TagHierarchyRequest tagHierarchyRequest = new TagHierarchyRequest();
//         tagHierarchyRequest.setTagId(tagId);
//         tagHierarchyRequest.setName(attributeName);
//         TagGroupExpressionRequest tagGroupExpressionRequest = new TagGroupExpressionRequest();
//         tagGroupExpressionRequest.setChannelLevel(request.getChannelLevel());
//         tagGroupExpressionRequest.setChannelCode(request.getChannelCode());
//         tagGroupExpressionRequest.setType("tagGroup");
//         tagGroupExpressionRequest.setTagGroupSource(2);
//         Object[] selectValue = new Object[]{attributeValue};
//         tagGroupSelect.setValue(selectValue);
//         tagGroupExpressionRequest.setSelects(Collections.singletonList(tagGroupSelect));
//         tagHierarchyRequest.setCalLogic(Collections.singletonList(tagGroupExpressionRequest));
//         TagHierarchyRequest tagHierarchyRequestCopy = JSON.parseObject(JSON.toJSONString(tagHierarchyRequest), TagHierarchyRequest.class);
//         tagsData.add(tagHierarchyRequestCopy);
//     }
//
//
//     /**
//      * 延迟编辑
//      */
//     @Override
//     public void delayEventTagEdit(DelayEventTagRequest delayEventTagRequest) {
//         //如果tags 不为空，直接插入
//         EditEventTagGroupRequest request = delayEventTagRequest.getEditRequest();
//         List<TagGroupSelect> calLogicNotEvent = delayEventTagRequest.getCalLogicNotEvent();
//         TagGroup tagGroup = delayEventTagRequest.getTagGroup();
//         List<TagHierarchyRequest> tags = delayEventTagRequest.getTags();
//         String linkApi = delayEventTagRequest.getApi();
//         TagGroupSelect tagGroupSelect = delayEventTagRequest.getTagGroupSelect();
//         Integer objType = delayEventTagRequest.getObjType();
//         Integer globalTagType = delayEventTagRequest.getGlobalTagType();
//         log.info("delayEventTagEdit globalTagType: {}", globalTagType);
//         List<Tag> byTagGroupId;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             byTagGroupId = tagCustomVipMapper.getByTagGroupId(tagGroup.getId());
//         } else {
//             byTagGroupId = tagCustomMapper.getByTagGroupId(tagGroup.getId());
//         }
//         //获取标签id
//         Map<Object, Integer> objIdMap = byTagGroupId.stream().collect(Collectors.toMap(Tag::getTagObjId, Tag::getId, (oldValue, newValue) -> newValue));
//         String time = delayEventTagRequest.getTime();
//         Integer doType = delayEventTagRequest.getDoType();
//         if (CollectionUtil.isNotEmpty(tags)) {
//             List<List<TagHierarchyRequest>> split = CollUtil.split(tags, 800);
//             for (List<TagHierarchyRequest> inList : split) {
//                 //拆分调用
//                 dealEventTagEdit(request, calLogicNotEvent, tagGroup, inList, objType);
//             }
//         } else {
//             //如果tags 为空，根据条件分页查询
//             //组装出数据
//             if (StrUtil.isNotBlank(linkApi)) {
//                 JSONObject linkApiJson = JSONObject.parseObject(linkApi);
//                 String apiUrl = linkApiJson.getString("url");
//                 if (StrUtil.isNotBlank(apiUrl)) {
//                     String[] split = apiUrl.split("/");
//                     String api = split[split.length - 1];
//
//                     if ("getproduct".equals(api)) {
//
//                         List<String> payProduct = new ArrayList<>();
//                         if (doType == 1) {
//                             payProduct = gpProductMapper.getPayProduct(time);
//                         } else if (doType == 2) {
//                             payProduct = gpProductMapper.getAddCartProduct(time);
//                         } else if (doType == 3) {
//                             payProduct = gpProductMapper.getViewProduct(time);
//                         } else if (doType == 4) {
//                             payProduct = gpProductMapper.getAddCartProduct(time);
//                         } else if (doType == 5) {
//                             payProduct = gpProductMapper.getViewProduct(time);
//                         }
//
//                         if (CollectionUtil.isNotEmpty(payProduct)) {
//                             List<List<String>> splitList = CollUtil.split(payProduct, 800);
//                             List<TagHierarchyRequest> tagsData;
//                             for (List<String> codes : splitList) {
//                                 List<ProductResponse> data = erpProductService.getProductListByCodes(codes);
//                                 if (CollectionUtil.isNotEmpty(data)) {
//                                     tagsData = new ArrayList<>();
//                                     //赋值页索引
//                                     if (CollectionUtil.isNotEmpty(data)) {
//                                         for (ProductResponse inData : data) {
//                                             //数据
//                                             String attributeValue = inData.getProductCode();
//                                             String attributeName = inData.getProductName();
//                                             Integer tagId = objIdMap.get(attributeValue);
//                                             dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                                         }
//                                         dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                     }
//                                 }
//                             }
//                         }
//                         //
//                         //
//                         //
//                         //    //调用方法，构造查询参数
//                         //    ProductRequest productRequest = new ProductRequest();
//                         //    //第一页，每次查询1000
//                         //    productRequest.setPageIndex(1);
//                         //    productRequest.setPageSize(1000);
//                         //    String channelTypeCode = Optional.ofNullable(linkApiJson.getJSONObject("apiParma")).map(j -> j.getString("channelTypeCode")).orElse(null);
//                         //    productRequest.setChannelTypeCode(channelTypeCode);
//                         //    PageResponse<ProductResponse> productByPage = erpProductService.getProductByPage(productRequest);
//                         //
//                         //    if (CollectionUtil.isNotEmpty(productByPage.getData())) {
//                         //        List<TagHierarchyRequest> tagsData;
//                         //        int dealCount = 1000;
//                         //        int timeCount = BigDecimal.valueOf(productByPage.getTotal()).divide(BigDecimal.valueOf(dealCount), 0, RoundingMode.UP).intValue();
//                         //        //页数大于1
//                         //        if (timeCount > 1) {
//                         //            //循环页索引,从第二页开始
//                         //            for (int i = 2; i <= timeCount; i++) {
//                         //                tagsData = new ArrayList<>();
//                         //                //赋值页索引
//                         //                productRequest.setPageIndex(i);
//                         //                PageResponse<ProductResponse> productByPageLimit = erpProductService.getProductByPage(productRequest);
//                         //
//                         //                List<ProductResponse> data = productByPageLimit.getData();
//                         //                if (CollectionUtil.isNotEmpty(data)) {
//                         //                    for (ProductResponse inData : data) {
//                         //                        //数据
//                         //                        String attributeValue = inData.getProductCode();
//                         //                        String attributeName = inData.getProductName();
//                         //                        Integer tagId = objIdMap.get(attributeValue);
//                         //                        dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                         //                    }
//                         //                    dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                         //                }
//                         //            }
//                         //        } else {
//                         //            List<ProductResponse> data = productByPage.getData();
//                         //            if (CollectionUtil.isNotEmpty(data)) {
//                         //                tagsData = new ArrayList<>();
//                         //                //组装
//                         //                for (ProductResponse inData : data) {
//                         //                    //数据
//                         //                    String attributeValue = inData.getProductCode();
//                         //                    String attributeName = inData.getProductName();
//                         //                    Integer tagId = objIdMap.get(attributeValue);
//                         //                    dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                         //                }
//                         //                //处理标签分层
//                         //                dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                         //            }
//                         //        }
//                         //
//                         //    }
//                         //}
//                     } else if ("getbrand".equals(api)) {
//                         //调用方法，构造查询参数
//                         BrandRequest brandRequest = new BrandRequest();
//                         //第一页，每次查询1000
//                         brandRequest.setPageIndex(1);
//                         brandRequest.setPageSize(1000);
//                         String channelTypeCode = Optional.ofNullable(linkApiJson.getJSONObject("apiParma")).map(j -> j.getString("channelTypeCode")).orElse(null);
//                         brandRequest.setChannelTypeCode(channelTypeCode);
//                         PageResponse<BrandResponse> brandByPage = erpProductService.getBrandByPage(brandRequest);
//                         if (CollectionUtil.isNotEmpty(brandByPage.getData())) {
//                             List<TagHierarchyRequest> tagsData;
//                             int dealCount = 1000;
//                             int timeCount = BigDecimal.valueOf(brandByPage.getTotal()).divide(BigDecimal.valueOf(dealCount), 0, RoundingMode.UP).intValue();
//                             //页数大于1
//                             if (timeCount > 1) {
//                                 //循环页索引,从第二页开始
//                                 for (int i = 2; i <= timeCount; i++) {
//                                     tagsData = new ArrayList<>();
//                                     //赋值页索引
//                                     brandRequest.setPageIndex(i);
//                                     PageResponse<BrandResponse> brandByPageLimit = erpProductService.getBrandByPage(brandRequest);
//
//                                     List<BrandResponse> data = brandByPageLimit.getData();
//                                     if (CollectionUtil.isNotEmpty(data)) {
//                                         for (BrandResponse inData : data) {
//                                             //数据
//                                             String attributeValue = inData.getBrandId();
//                                             String attributeName = inData.getBrandName();
//                                             Integer tagId = objIdMap.get(attributeValue);
//                                             dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                                         }
//                                         dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                     }
//                                 }
//                             } else {
//                                 List<BrandResponse> data = brandByPage.getData();
//                                 if (CollectionUtil.isNotEmpty(data)) {
//                                     tagsData = new ArrayList<>();
//                                     //组装
//                                     for (BrandResponse inData : data) {
//                                         //数据
//                                         String attributeValue = inData.getBrandId();
//                                         String attributeName = inData.getBrandName();
//                                         Integer tagId = objIdMap.get(attributeValue);
//                                         dealEditLayer(request, tagGroupSelect, tagsData, attributeValue, attributeName, tagId);
//                                     }
//                                     //处理标签
//                                     dealEventTagEdit(request, calLogicNotEvent, tagGroup, tagsData, objType);
//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//         //最后发送mq
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             rabbitTemplate.convertAndSend(VIP_TAG_QUEUE, String.valueOf(tagGroup.getId()));
//         } else {
//             rabbitTemplate.convertAndSend(TAG_QUEUE, String.valueOf(tagGroup.getId()));
//         }
//     }
//
//
//     /**
//      * 处理编辑
//      *
//      * @param request
//      * @param calLogicNotEvent
//      * @param tagGroup
//      * @param tags
//      */
//     private void dealEventTagEdit(EditEventTagGroupRequest request, List<TagGroupSelect> calLogicNotEvent, TagGroup tagGroup, List<TagHierarchyRequest> tags, Integer objType) {
//         List<TagGroupUseRelation> relationList = new ArrayList<>();
//         Integer globalTagType = request.getGlobalTagType();
//         //查询原有分层比对
//         //List<Tag> tagList = tagCustomMapper.getByTagGroupId(tagGroup.getId());
//         List<Integer> ids = tags.stream().map(TagHierarchyRequest::getTagId).collect(Collectors.toList());
//         //根据分层id列表获取分层，进行比对，如果是不限，删除的数据不管，如果限制不能修改则不管，新增的数据入库，其余数据修改名称
//         List<Tag> tagList;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagList = tagCustomVipMapper.getByTagIds(ids);
//         } else {
//             tagList = tagCustomMapper.getByTagIds(ids);
//         }
//         //现有分层（编辑过的）
//         Map<Integer, List<TagHierarchyRequest>> tagHierarchyMap = tags.stream().filter(t -> ObjectUtil.isNotNull(t.getTagId())).collect(Collectors.groupingBy(TagHierarchyRequest::getTagId));
//         //遍历原分层
//         Tag tag;
//         for (Tag inTag : tagList) {
//             List<TagHierarchyRequest> tagHierarchyRequestList = tagHierarchyMap.get(inTag.getId());
//             //比对分层是否已经被删除
//             if (CollectionUtil.isNotEmpty(tagHierarchyRequestList)) {
//                 TagHierarchyRequest tagHierarchyRequest = tagHierarchyRequestList.get(0);
//                 Integer tagId = tagHierarchyRequest.getTagId();
//                 List<TagGroupExpressionRequest> calLogic = tagHierarchyRequest.getCalLogic();
//                 boolean b = checkEx(calLogic);
//                 if (!b) {
//                     throw new Http400Exception("layerExpressionIllegal", "分层[" + tagHierarchyRequest.getName() + "]公式不合法");
//                 }
//                 List<TagGroupSelect> selectList = tagHierarchyRequest.getCalLogic().get(0).getSelects();
//                 //selectList.addAll(calLogicNotEvent);
//                 //处理对象至末尾
//                 List<TagGroupSelect> list = new ArrayList<>();
//                 list.addAll(calLogicNotEvent);
//                 list.addAll(selectList);
//                 TagGroupExpressionRequest tagGroupExpressionRequest = tagHierarchyRequest.getCalLogic().get(0);
//                 tagGroupExpressionRequest.setSelects(list);
//                 // 设置全局标签类型属性
//                 calLogic.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                 String sql = buildQueryAllChannel(calLogic, tagGroup.getDataRange());
//                 if (StrUtil.isBlank(sql)) {
//                     throw new Http400Exception("layerExpressionGenerateError", "分层[" + tagHierarchyRequest.getName() + "]公式生成错误");
//                 }
//                 //判断分层内关系， 更新分层关系
//                 List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//                 for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                     if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                         List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                         Integer groupId = tagGroupRequest.getTagGroupId();
//                         if (groupId.equals(tagGroup.getId())) {
//                             throw new Http400Exception("selfError", "无法引用自身");
//                         }
//                         TagGroupSelect select = selects.get(0);
//                         Object value = select.getValue();
//                         if (ObjectUtil.isNotNull(value)) {
//                             TagGroupUseRelation relation;
//                             List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                             for (Integer id : listValue) {
//                                 relation = new TagGroupUseRelation();
//                                 relation.setTagId(tagId);
//                                 relation.setTagGroupId(request.getTagGroupId());
//                                 relation.setUseTagId(id);
//                                 relation.setUseTagGroupId(groupId);
//                                 relation.setIsDeleted(0);
//                                 relationList.add(relation);
//                             }
//                         }
//                     }
//                 }
//                 Object obj = Optional.ofNullable(selectList.get(0)).map(TagGroupSelect::getValue).map(t -> ((JSONArray) t).get(0)).orElse(null);
//                 //更新分层
//                 tag = new Tag();
//                 tag.setId(tagId);
//                 tag.setTagName(tagHierarchyRequest.getName());
//                 tag.setDescribe(tagHierarchyRequest.getDescription());
//                 tag.setTagGroupId(tagGroup.getId());
//                 tag.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//                 tag.setSqlQuery(sql);
//                 tag.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//                 tag.setChannelCode(request.getChannelCode());
//                 tag.setChannelLevel(request.getChannelLevel());
//                 tag.setSort(tagHierarchyRequest.getSort());
//                 tag.setUpdateTime(new Date());
//                 tag.setUpdater(request.getUserId());
//                 tag.setCreaterName(request.getUserName());
//                 tag.setTagObjId(String.valueOf(obj));
//                 tag.setTagObjType(objType);
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.updateTagById(tag);
//                     //删除之前的引用
//                     tagGroupRelationCustomVipMapper.deleteRelationByTagId(tagId);
//                     //添加新的引用
//                     if (CollectionUtil.isNotEmpty(relationList)) {
//                         tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationList);
//                     }
//                     try {
//                         if (request.getIsSync() == null || request.getIsSync() == 0) {
//                             // 级联更新的编辑标志
//                             redisTemplate.opsForValue().set(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                         }
//                     } catch (Exception e) {
//                         log.error("set redis fail:tagId:" + tag.getId(), e);
//                         redisTemplate.delete(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tag.getId());
//                     }
//                 } else {
//                     tagCustomMapper.updateTagById(tag);
//                     //删除之前的引用
//                     tagGroupRelationCustomMapper.deleteRelationByTagId(tagId);
//                     //添加新的引用
//                     if (CollectionUtil.isNotEmpty(relationList)) {
//                         tagGroupRelationCustomMapper.insertTagGroupRelation(relationList);
//                     }
//                     try {
//                         if (request.getIsSync() == null || request.getIsSync() == 0) {
//                             // 级联更新的编辑标志
//                             redisTemplate.opsForValue().set(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId(), 1, 2, TimeUnit.HOURS);
//                         }
//                     } catch (Exception e) {
//                         log.error("set redis fail:tagId:" + tag.getId(), e);
//                         redisTemplate.delete(TagConstants.TAG_EDIT_KEY_PREFIX + tag.getId());
//                     }
//                 }
//
//             } else {
//                 // 查询分层是否有被引用
//                 TagRelationInfo tagRelationInfo;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagRelationInfo = tagRelationInfoVipDao.queryByIdType(Long.valueOf(inTag.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 } else {
//                     tagRelationInfo = tagRelationInfoDao.queryByIdType(Long.valueOf(inTag.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 }
//                 if (null != tagRelationInfo) {
//                     if (!CollectionUtils.isEmpty(tagRelationInfo.getPassiveCrowdIds()) || !CollectionUtils.isEmpty(tagRelationInfo.getPassiveTagIds())) {
//                         throw new Http400Exception("delete tag fail!", "分层被标签或者人群引用");
//                     }
//                 }
//                 //删除标签，及引用关系
//                 Tag tagDel = new Tag();
//                 tagDel.setId(inTag.getId());
//                 tagDel.setIsDeleted(1);
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.updateTagById(tagDel);
//                     tagGroupRelationCustomVipMapper.deleteRelationByTagId(inTag.getId());
//                 } else {
//                     tagCustomMapper.updateTagById(tagDel);
//                     tagGroupRelationCustomMapper.deleteRelationByTagId(inTag.getId());
//                 }
//             }
//         }
//         //不带tagId,新增的分层
//         List<TagHierarchyRequest> addHierarchy = tags.stream().filter(t -> ObjectUtil.isNull(t.getTagId())).collect(Collectors.toList());
//         if (CollectionUtil.isNotEmpty(addHierarchy)) {
//             //新增
//             List<TagGroupUseRelation> relationAddList = new ArrayList<>();
//             for (TagHierarchyRequest tagHierarchyRequest : addHierarchy) {
//                 List<TagGroupExpressionRequest> calLogic = tagHierarchyRequest.getCalLogic();
//                 boolean b = checkEx(calLogic);
//                 if (!b) {
//                     throw new Http400Exception("layerExpressionIllegal", "分层[" + tagHierarchyRequest.getName() + "]公式不合法");
//                 }
//                 List<TagGroupSelect> selectList = tagHierarchyRequest.getCalLogic().get(0).getSelects();
//                 //selectList.addAll(calLogicNotEvent);
//                 //处理对象至末尾
//                 List<TagGroupSelect> list = new ArrayList<>();
//                 list.addAll(calLogicNotEvent);
//                 list.addAll(selectList);
//                 TagGroupExpressionRequest tagGroupExpressionRequest = tagHierarchyRequest.getCalLogic().get(0);
//                 tagGroupExpressionRequest.setSelects(list);
//                 Object obj = Optional.ofNullable(selectList.get(0)).map(TagGroupSelect::getValue).map(t -> ((JSONArray) t).get(0)).orElse(null);
//                 // 设置全局标签类型属性
//                 calLogic.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                 String sql = buildQueryAllChannel(calLogic, tagGroup.getDataRange());
//                 if (StrUtil.isBlank(sql)) {
//                     throw new Http400Exception("layerExpressionGenerateError", "分层[" + tagHierarchyRequest.getName() + "]公式生成错误");
//                 }
//                 Tag tagAdd = new Tag();
//                 tagAdd.setTagName(tagHierarchyRequest.getName());
//                 tagAdd.setDescribe(tagHierarchyRequest.getDescription());
//                 tagAdd.setTagGroupId(tagGroup.getId());
//                 tagAdd.setExpression(JSON.toJSONString(tagHierarchyRequest.getCalLogic()));
//                 tagAdd.setSqlQuery(sql);
//                 tagAdd.setDealStatus(TagDelStatusEnum.CALCULATE_WAIT.getDealStatus());
//                 tagAdd.setChannelCode(request.getChannelCode());
//                 tagAdd.setChannelLevel(request.getChannelLevel());
//                 tagAdd.setLayerCount(tagHierarchyRequest.getLayerCount() == null ? 0 : tagHierarchyRequest.getLayerCount());
//                 tagAdd.setCountUpdateTime(new Date());
//                 tagAdd.setSort(tagHierarchyRequest.getSort() == null ? 0 : tagHierarchyRequest.getSort());
//                 tagAdd.setCreateTime(new Date());
//                 tagAdd.setCreater(request.getUserId());
//                 tagAdd.setCreaterName(request.getUserName());
//                 tagAdd.setIsDeleted(0);
//                 tagAdd.setTagObjId(String.valueOf(obj));
//                 tagAdd.setTagObjType(objType);
//                 //分层循环新增,需要返回的主键id做后续操作
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagCustomVipMapper.insertTag(tagAdd);
//                     try {
//                         if (request.getIsSync() == null || request.getIsSync() == 0) {
//                             // 级联更新的编辑标志
//                             redisTemplate.opsForValue().set(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tagAdd.getId(), 1, 2, TimeUnit.HOURS);
//                         }
//                     } catch (Exception e) {
//                         log.error("set redis fail:tagId:" + tagAdd.getId(), e);
//                         redisTemplate.delete(TagConstants.TAG_VIP_EDIT_KEY_PREFIX + tagAdd.getId());
//                     }
//                 } else {
//                     tagCustomMapper.insertTag(tagAdd);
//                     try {
//                         if (request.getIsSync() == null || request.getIsSync() == 0) {
//                             // 级联更新的编辑标志
//                             redisTemplate.opsForValue().set(TagConstants.TAG_EDIT_KEY_PREFIX + tagAdd.getId(), 1, 2, TimeUnit.HOURS);
//                         }
//                     } catch (Exception e) {
//                         log.error("set redis fail:tagId:" + tagAdd.getId(), e);
//                         redisTemplate.delete(TagConstants.TAG_EDIT_KEY_PREFIX + tagAdd.getId());
//                     }
//                 }
//                 List<TagGroupExpressionRequest> logic = tagHierarchyRequest.getCalLogic();
//                 TagGroupUseRelation relation = new TagGroupUseRelation();
//
//                 for (TagGroupExpressionRequest tagGroupRequest : logic) {
//                     if (ObjectUtil.isNotNull(tagGroupRequest.getTagGroupSource()) && tagGroupRequest.getTagGroupSource() == 1) {
//                         List<TagGroupSelect> selects = tagGroupRequest.getSelects();
//                         TagGroupSelect select = selects.get(0);
//                         Object value = select.getValue();
//                         if (ObjectUtil.isNotNull(value)) {
//                             List<Integer> listValue = JSON.parseArray(value.toString(), Integer.class);
//                             for (Integer id : listValue) {
//                                 relation.setTagId(tagAdd.getId());
//                                 relation.setTagGroupId(tagGroup.getId());
//                                 relation.setUseTagId(id);
//                                 relation.setUseTagGroupId(tagGroupRequest.getTagGroupId());
//                                 relation.setIsDeleted(0);
//                                 relationAddList.add(relation);
//                             }
//                         }
//                     }
//                 }
//             }
//             //插入引用
//             if (CollectionUtil.isNotEmpty(relationAddList)) {
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagGroupRelationCustomVipMapper.insertTagGroupRelation(relationAddList);
//                 } else {
//                     tagGroupRelationCustomMapper.insertTagGroupRelation(relationAddList);
//                 }
//             }
//         }
//         //查询被哪些其他标签组引用,更新这些标签组
//         List<TagGroupUseRelation> tagGroupBeUsedRelations;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupBeUsedRelations = tagGroupRelationCustomVipMapper.getTagGroupBeUsedRelation(tagGroup.getId());
//         } else {
//             tagGroupBeUsedRelations = tagGroupRelationCustomMapper.getTagGroupBeUsedRelation(tagGroup.getId());
//         }
//         if (CollectionUtil.isNotEmpty(tagGroupBeUsedRelations)) {
//             for (TagGroupUseRelation relation : tagGroupBeUsedRelations) {
//                 //更新被引用标签
//                 Integer relationTagGroupId = relation.getTagGroupId();
//                 List<Tag> byTagGroupId;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     byTagGroupId = tagCustomVipMapper.getByTagGroupId(relationTagGroupId);
//                 } else {
//                     byTagGroupId = tagCustomMapper.getByTagGroupId(relationTagGroupId);
//                 }
//                 if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//                     for (Tag inTag : byTagGroupId) {
//                         String expression = inTag.getExpression();
//                         List<TagGroupExpressionRequest> requests = JSON.parseArray(expression, TagGroupExpressionRequest.class);
//                         // 设置全局标签类型属性
//                         requests.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                         String newSql = buildQueryAllChannel(requests, tagGroup.getDataRange());
//                         inTag.setSqlQuery(newSql);
//                         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                             // 会员标签
//                             tagCustomVipMapper.updateTagById(inTag);
//                         } else {
//                             tagCustomMapper.updateTagById(inTag);
//                         }
//                     }
//                 }
//             }
//         }
//         if (request.getIsSync() == null || request.getIsSync() == 0) {
//             TagGroupLog tagGroupLog = new TagGroupLog();
//             tagGroupLog.setTagGroupId(tagGroup.getId());
//             tagGroupLog.setActionType(TagGroupActionEnum.UPDATE.getAction());
//             tagGroupLog.setActionTime(new Date());
//             tagGroupLog.setCreater(request.getUserId());
//             tagGroupLog.setCreaterName(request.getUserName());
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupLogVipMapper.insertLog(tagGroupLog);
//             } else {
//                 tagGroupLogMapper.insertLog(tagGroupLog);
//             }
//         }
//         //发mq
//         //rabbitTemplate.convertAndSend(TAG_QUEUE, request.getTagGroupId());
//         //sendMsg(TAG_QUEUE, request.getTagGroupId());
//     }
//
//
//     @Override
//     @Transactional(rollbackFor = Exception.class)
//     public CommonResponse deleteTagGroup(TagGroupIdRequest request) {
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("deletingTagGroup globalTagType: {}", globalTagType);
//         //查询引用
//         List<TagGroupUseRelation> tagGroupBeUsedRelations;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupBeUsedRelations = tagGroupRelationCustomVipMapper.getTagGroupBeUsedRelation(request.getTagGroupId());
//         } else {
//             tagGroupBeUsedRelations = tagGroupRelationCustomMapper.getTagGroupBeUsedRelation(request.getTagGroupId());
//         }
//         if (CollectionUtil.isNotEmpty(tagGroupBeUsedRelations)) {
//             List<Integer> tagGroupIds = tagGroupBeUsedRelations.stream().map(TagGroupUseRelation::getTagGroupId).collect(Collectors.toList());
//             if (CollectionUtil.isNotEmpty(tagGroupIds)) {
//                 List<String> tagGroupNames;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagGroupNames = tagGroupCustomVipMapper.getTagGroupNamesByIds(tagGroupIds);
//                 } else {
//                     tagGroupNames = tagGroupCustomMapper.getTagGroupNamesByIds(tagGroupIds);
//                 }
//                 //被其他引用，无法删除
//                 throw new Http400Exception("layerAlreadyReferenced", "标签组被其他标签组引用无法删除：[" + StringUtils.join(tagGroupNames, ';') + "]");
//             }
//         }
//         //更新标签组跟标签的删除状态
//         TagGroup tagGroupById;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupById = tagGroupCustomVipMapper.getTagGroupById(request.getTagGroupId());
//         } else {
//             tagGroupById = tagGroupCustomMapper.getTagGroupById(request.getTagGroupId());
//         }
//         Integer groupSource = Optional.ofNullable(tagGroupById).map(TagGroup::getTagGroupSource).orElse(null);
//         if (groupSource != null && groupSource != 1) {
//             throw new Http400Exception("deleteError", "非自定义标签无法删除");
//         }
//         List<Integer> tagIds = new ArrayList<>();
//         // 确认最终引用信息
//         List<TagRelationInfo> tagRelationInfos = new ArrayList<>();
//         try {
//             List<Tag> byTagGroupIds;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 byTagGroupIds = tagCustomVipMapper.getByTagGroupId(request.getTagGroupId());
//             } else {
//                 byTagGroupIds = tagCustomMapper.getByTagGroupId(request.getTagGroupId());
//             }
//             if (!CollectionUtils.isEmpty(byTagGroupIds)) {
//                 for (Tag byTagGroupId : byTagGroupIds) {
//                     tagIds.add(byTagGroupId.getId());
//                 }
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tagRelationInfos = tagRelationInfoVipDao.batchQueryByIdTypes(tagIds, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 } else {
//                     tagRelationInfos = tagRelationInfoDao.batchQueryByIdTypes(tagIds, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 }
//                 if (!CollectionUtils.isEmpty(tagRelationInfos)) {
//                     for (TagRelationInfo tagRelationInfo : tagRelationInfos) {
//                         if (!CollectionUtils.isEmpty(tagRelationInfo.getPassiveCrowdIds()) || !CollectionUtils.isEmpty(tagRelationInfo.getPassiveTagIds())) {
//                             //被其他引用，无法删除
//                             throw new Http400Exception("tagGroup is being used", "标签组被引用无法删除");
//                         }
//                     }
//                 }
//             }
//         } catch (Http400Exception e) {
//             throw new Http400Exception(e.getErrorCode(), e.getMessage());
//         } catch (Exception e) {
//             log.error("deal delete byTagGroupIds error:", e);
//             throw e;
//         }
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(request.getTagGroupId());
//         tagGroup.setIsDeleted(1);
//         Tag tag = new Tag();
//         tag.setTagGroupId(request.getTagGroupId());
//         tag.setIsDeleted(1);
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//             tagCustomVipMapper.updateTagDeletedByTagGroupId(tag);
//             //删除引用其他标签组的关系
//             tagGroupRelationCustomVipMapper.updateRelationDeleteByTagGroupId(request.getTagGroupId());
//         } else {
//             tagGroupCustomMapper.updateTagGroupById(tagGroup);
//             tagCustomMapper.updateTagDeletedByTagGroupId(tag);
//             //删除引用其他标签组的关系
//             tagGroupRelationCustomMapper.updateRelationDeleteByTagGroupId(request.getTagGroupId());
//         }
//         //删除引用其他标签组的关系
//         updateTagRelation(tagIds, tagRelationInfos, globalTagType);
//         TagGroupLog tagGroupLog = new TagGroupLog();
//         tagGroupLog.setTagGroupId(tagGroup.getId());
//         tagGroupLog.setActionType(TagGroupActionEnum.DELETE.getAction());
//         tagGroupLog.setActionTime(new Date());
//         tagGroupLog.setCreater(request.getUserId());
//         tagGroupLog.setCreaterName(request.getUserName());
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagGroupLogVipMapper.insertLog(tagGroupLog);
//         } else {
//             tagGroupLogMapper.insertLog(tagGroupLog);
//         }
//         // 删除标签维度表中，该标签组下面的所有的一级业务渠道，平台，店铺维度的标签
//         if (!Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             tagDimensionMapper.deleteDimensionTagByTagGroupId(request.getTagGroupId(), request.getUserId(), request.getUserName());
//         }
//         return new CommonResponse(true);
//     }
//
//
//     /**
//      * 删除的标签分层更新引用
//      *
//      * @param tagIds
//      * @param tagRelationInfos
//      */
//     private void updateTagRelation(List<Integer> tagIds, List<TagRelationInfo> tagRelationInfos, Integer globalTagType) {
//         if (!CollectionUtils.isEmpty(tagIds)) {
//             List<Integer> useTagIds = new ArrayList<>();
//             if (!CollectionUtils.isEmpty(tagRelationInfos)) {
//                 for (TagRelationInfo relationInfo : tagRelationInfos) {
//                     if (null != relationInfo) {
//                         //查询哪一些分层使用到了这个标签组
//                         if (CollectionUtil.isNotEmpty(relationInfo.getTagIds())) {
//                             useTagIds.addAll(relationInfo.getTagIds());
//                         }
//                     }
//                 }
//             }
//             if (CollectionUtil.isNotEmpty(useTagIds)) {
//                 List<TagRelationInfo> useTagRelations;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     useTagRelations = tagRelationInfoVipDao.batchQueryByIdTypes(useTagIds, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 } else {
//                     useTagRelations = tagRelationInfoDao.batchQueryByIdTypes(useTagIds, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                 }
//                 if (!CollectionUtils.isEmpty(useTagRelations)) {
//                     for (TagRelationInfo relationInfo : useTagRelations) {
//                         if (null != relationInfo) {
//                             //查询哪一些分层使用到了这个标签组
//                             if (CollectionUtil.isNotEmpty(relationInfo.getPassiveTagIds())) {
//                                 List<Integer> passiveTagIds = relationInfo.getPassiveTagIds();
//                                 passiveTagIds.removeAll(tagIds);
//                                 relationInfo.setPassiveTagIds(passiveTagIds);
//                             }
//                         }
//                     }
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagRelationInfoVipDao.batchUpdateByIdTypes(useTagRelations);
//                     } else {
//                         tagRelationInfoDao.batchUpdateByIdTypes(useTagRelations);
//                     }
//                 }
//             }
//         }
//     }
//
//     @Override
//     public CommonResponse checkGroupName(TagGroupNameRequest request) {
//         int tagGroupByName;
//         if (Objects.equals(VIP_TAG.getType(), request.getGlobalTagType())) {
//             // 会员标签
//             tagGroupByName = tagGroupCustomVipMapper.getTagGroupByName(request);
//         } else {
//             tagGroupByName = tagGroupCustomMapper.getTagGroupByName(request);
//         }
//         return new CommonResponse(tagGroupByName < 1);
//     }
//
//     @Override
//     public CommonResponse syncAllTagGroup() {
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 ExecutorService executor = Executors.newSingleThreadExecutor();
//                 Future<?> future = executor.submit(() -> {
//                     userTagService.moreCateUserTagAggTask();
//                     userTagService.userTagAggTask();
//                 });
//                 try {
//                     //如果超过5分钟还没有完成，则直接抛出异常，继续下一个任务
//                     future.get(10, TimeUnit.MINUTES);
//                     doCrowdAndTag();
//                 } catch (Exception e) {
//                     log.error("同步用户标签超过5分钟", e);
//                     doCrowdAndTag();
//                 } finally {
//                     // 及时关闭ExecutorService
//                     executor.shutdown();
//                 }
//             }
//         }).start();
//         return new CommonResponse(true);
//     }
//
//     @Override
//     public CommonResponse syncAllVipTagGroup() {
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 ExecutorService executor = Executors.newSingleThreadExecutor();
//                 try {
//                     //如果超过5分钟还没有完成，则直接抛出异常，继续下一个任务
//                     doVipTag();
//                 } catch (Exception e) {
//                     log.error("syncAllVipTagGroup 同步会员标签超过5分钟", e);
//                     doVipTag();
//                 } finally {
//                     // 及时关闭ExecutorService
//                     executor.shutdown();
//                 }
//             }
//         }).start();
//         return new CommonResponse(true);
//     }
//
//     /**
//      * 异步同步人群跟标签
//      */
//     public void doCrowdAndTag() {
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 try {
//                     log.info("全量同步开始 >>>>>>>>>>> ");
//                     // mq跑人群数据
//                     try {
//                         new Thread(new Runnable() {
//                             @Override
//                             public void run() {
//                                 // 执行人群的定时任务
//                                 crowdScheduleService.dealEachDay();
//                             }
//                         }).start();
//                     } catch (Exception e) {
//                         log.error("send MQ fail! param is sync all Crowd", e);
//                     }
//                     //同步标签
//                     List<TagGroup> allGroup = tagGroupCustomMapper.getAll();
//                     doSyncTagGroup(allGroup);
//                 } catch (Exception e) {
//                     log.error("send MQ fail! param is sync all Crowd", e);
//                 }
//             }
//         }).start();
//     }
//
//
//     /**
//      * 会员标签全量同步
//      */
//     public void doVipTag() {
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 try {
//                     log.info("会员标签全量同步 >>>>>>>>>>> ");
//                     //同步标签
//                     List<TagGroup> allGroup = tagGroupCustomVipMapper.getAll();
//                     doSyncVipTagGroup(allGroup);
//                 } catch (Exception e) {
//                     log.error("doVipTag send MQ fail! param is sync all Crowd", e);
//                 }
//             }
//         }).start();
//     }
//
//
//     /**
//      * 同步任意个标签组
//      *
//      * @param request
//      * @return
//      */
//     @Override
//     public CommonResponse syncAnyTagGroup(TagGroupIdsRequest request) {
//         Integer globalTagType = request.getGlobalTagType();
//         if (CollectionUtil.isNotEmpty(request.getIds())) {
//             log.info("syncAnyTagGroup globalTagType: {}, groupIds: {}", globalTagType, request.getIds());
//             List<TagGroup> allGroup;
//             if (Objects.equals(VIP_TAG.getType(), request.getGlobalTagType())) {
//                 // 会员标签
//                 allGroup = tagGroupCustomVipMapper.getTagGroupByIds(request.getIds());
//                 doSyncVipTagGroup(allGroup);
//             } else {
//                 allGroup = tagGroupCustomMapper.getTagGroupByIds(request.getIds());
//                 doSyncTagGroup(allGroup);
//             }
//         }
//         return new CommonResponse(true);
//     }
//
//     private void doSyncTagGroup(List<TagGroup> allGroup) {
//         if (CollectionUtil.isNotEmpty(allGroup)) {
//             for (TagGroup group : allGroup) {
//                 try {
//                     Integer isNotLimit = group.getIsNotLimit();
//                     if (ObjectUtil.isNotNull(isNotLimit) && isNotLimit == 1) {
//                         //如果是不限，维表每天会变，重新编辑一下
//                         String eventParam = group.getEventParam();
//                         if (StrUtil.isNotBlank(eventParam)) {
//                             EditEventTagGroupRequest editEventTagGroupRequest = JSONObject.parseObject(eventParam, EditEventTagGroupRequest.class);
//                             editEventTagGroupRequest.setTagGroupCateId(group.getTagGroupCateId());
//                             editEventTagGroupRequest.setIsSync(1);
//                             try {
//                                 tagService.editEventTagGroup(editEventTagGroupRequest);
//                             } catch (Exception e) {
//                                 TagGroup tagGroup = new TagGroup();
//                                 tagGroup.setId(group.getId());
//                                 tagGroup.setDealStatus(TagGroupDelStatusEnum.CREATE_FAIL.getDealStatus());
//                                 tagGroupCustomMapper.updateTagGroupById(tagGroup);
//                                 log.error("同步事件偏好不限失败，参数[{}]", eventParam, e);
//                             }
//                             log.info("对象不限标签组,标签组id[{}]", group.getId());
//                         } else {
//                             log.error("对象不限标签组错误:无入参,标签组id[{}]", group.getId());
//                         }
//                     } else {
//                         rabbitTemplate.convertAndSend(TAG_QUEUE, group.getId().toString());
//                         log.info("发送成功,标签组id[{}]", group.getId());
//                     }
//                 } catch (Exception e) {
//                     log.error("发送失败,标签组id[{}],异常:[{}]", group.getId(), e);
//                 }
//             }
//         }
//     }
//
//     private void doSyncVipTagGroup(List<TagGroup> allGroup) {
//         if (CollectionUtil.isNotEmpty(allGroup)) {
//             for (TagGroup group : allGroup) {
//                 try {
//                     Integer isNotLimit = group.getIsNotLimit();
//                     if (ObjectUtil.isNotNull(isNotLimit) && isNotLimit == 1) {
//                         // 如果是不限，维表每天会变，重新编辑一下
//                         String eventParam = group.getEventParam();
//                         if (StrUtil.isNotBlank(eventParam)) {
//                             EditEventTagGroupRequest editEventTagGroupRequest = JSONObject.parseObject(eventParam, EditEventTagGroupRequest.class);
//                             editEventTagGroupRequest.setTagGroupCateId(group.getTagGroupCateId());
//                             editEventTagGroupRequest.setIsSync(1);
//                             editEventTagGroupRequest.setGlobalTagType(VIP_TAG.getType());
//                             try {
//                                 tagService.editEventTagGroup(editEventTagGroupRequest);
//                             } catch (Exception e) {
//                                 TagGroup tagGroup = new TagGroup();
//                                 tagGroup.setId(group.getId());
//                                 tagGroup.setDealStatus(TagGroupDelStatusEnum.CREATE_FAIL.getDealStatus());
//                                 tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//                                 log.error("同步事件偏好不限失败，参数[{}]", eventParam, e);
//                             }
//                             log.info("对象不限标签组,会员标签组id[{}]", group.getId());
//                         } else {
//                             log.error("对象不限标签组错误:无入参,会员标签组id[{}]", group.getId());
//                         }
//                     } else {
//                         rabbitTemplate.convertAndSend(VIP_TAG_QUEUE, group.getId().toString());
//                         log.info("发送成功,会员标签组id[{}]", group.getId());
//                     }
//                 } catch (Exception e) {
//                     log.error("发送失败,会员标签组id[{}],异常:[{}]", group.getId(), e);
//                 }
//             }
//         }
//     }
//
//     /**
//      * 全渠道的
//      *
//      * @param requests
//      * @return
//      */
//     public String buildQueryAllChannel(List<TagGroupExpressionRequest> requests, String dataRange) {
//         String queryBuilder = null;
//         Integer globalTagType = null;
//         if (CollectionUtil.isNotEmpty(requests)) {
//             for (TagGroupExpressionRequest request : requests) {
//                 request.setDataRange(dataRange);
//             }
//             // 全局标签类型，1：会员标签
//             globalTagType = requests.get(0).getGlobalTagType();
//             log.info("buildQueryAllChannel globalTagType: {}", globalTagType);
//             //校验引用关系
//             checkRelation(requests, globalTagType);
//             //校验表达式
//             boolean b = checkEx(requests);
//             if (!b) {
//                 throw new Http400Exception("expressionError", "标签表达式不合法，请检查标签/运算符/括号是否匹配且完整");
//             }
//             List<TagGroupExpressionRequest> listTmp = new ArrayList<>();
//             List<TagGroupExpressionRequest> tagExpressions = requests;
//             for (TagGroupExpressionRequest request : tagExpressions) {
//                 TagGroupExpressionRequest tagGroupExpressionRequest = new TagGroupExpressionRequest();
//                 BeanUtils.copyProperties(request, tagGroupExpressionRequest);
//                 // 获取数据范围相关的信息(只有标签才有数据范围)
//                 initRangeDataInfo(tagGroupExpressionRequest);
//                 listTmp.add(tagGroupExpressionRequest);
//             }
//             //补括号
//             List<TagGroupExpressionRequest> list = convertBrackets(listTmp);
//             //生成内部sql
//             queryBuilder = doSql(list).toString();
//         }
//         // 是否走oneId表
//         if (Objects.equals(tagRefreshConstants.getOneIdFlag(), 1) && !Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 初始化oneId表
//             queryBuilder = OneIdTableUtils.initOneIdTable(queryBuilder, tagRefreshConstants);
//         }
//         //测试
//         queryBuilder = queryBuilder.replaceAll("\\n", " ");
//         queryBuilder = queryBuilder.replaceAll("[\\s]+", " ");
//         return queryBuilder;
//     }
//
//     /**
//      * 处理数据范围
//      *
//      * @param tagGroupExpressionRequest
//      */
//     private void initRangeDataInfo(TagGroupExpressionRequest tagGroupExpressionRequest) {
//         try {
//             // 判空
//             if (null != tagGroupExpressionRequest && !CollectionUtils.isEmpty(tagGroupExpressionRequest.getSelects())) {
//                 List<TagGroupSelect> selects = tagGroupExpressionRequest.getSelects();
//                 // 标签id对应的select 信息
//                 List<TagGroupSelect> tmpSelects = new ArrayList<>();
//                 List<TagGroupSelect> tmpChannelTypeSelects = new ArrayList<>();
//                 Object objStoreValue = null;
//                 for (TagGroupSelect select : selects) {
//                     if (null != select) {
//                         if (Objects.equals(select.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_STORE_CODE)) {
//                             select.setAttributeId(tagRefreshConstants.getChannelStoreCodeAttributeId());
//                             tmpChannelTypeSelects.add(select);
//                             objStoreValue = select.getValue();
//                         } else if (Objects.equals(select.getAttributeCode(), TagConstants.ATTRIBUTE_CHANNEL_CODE)) {
//                             select.setAttributeId(tagRefreshConstants.getChannelTypeCodeAttributeId());
//                             tmpChannelTypeSelects.add(select);
//                         } else if (Objects.equals(select.getAttributeCode(), TagConstants.ATTRIBUTE_SALE_STORE_CODE)) {
//                             // 添加平台字段
//                             select.setAttributeId(tagRefreshConstants.getSaleStoreCodeAttributeId());
//                             tmpChannelTypeSelects.add(select);
//                         } else {
//                             tmpSelects.add(select);
//                         }
//                     }
//                 }
//                 // 处理首末次支付到店铺的问题
//                 if (null != objStoreValue) {
//                     for (TagGroupSelect select : selects) {
//                         if (null != select) {
//                             select.setStoreValue(objStoreValue);
//                         }
//                     }
//                 }
//                 tagGroupExpressionRequest.setSelects(tmpSelects);
//                 try {
//                     if (Objects.equals(tagRefreshConstants.getRangeDataTagFlag(), 0)) {
//                         String tmpChannelTypeSelectsStr = JSON.toJSONString(tmpChannelTypeSelects);
//                         tagGroupExpressionRequest.setDataRangeTagFlagInfo(tmpChannelTypeSelectsStr);
//                     }
//                 } catch (Exception e) {
//                     log.error(" ChannelTypeSelectsStr convert error:" + JSON.toJSONString(tagGroupExpressionRequest), e);
//                 }
//
//             }
//         } catch (Exception e) {
//             log.error("rangeData Convert fail:", e);
//         }
//     }
//
//     /**
//      * 自内向外，补上括号
//      *
//      * @param listAll
//      * @return
//      */
//     public List<TagGroupExpressionRequest> convertBrackets(List<TagGroupExpressionRequest> listAll) {
//         LinkedList boolStack = new LinkedList();
//         TagGroupExpressionRequest expression;
//         List list;
//         for (int i = 0; i < listAll.size(); i++) {
//             expression = listAll.get(i);
//             if (SYMBOL.getType().equals(expression.getType()) && CLOSE_PAREN.getExpression().equals(expression.getSymbol())) {
//                 int n = 0;
//                 list = new ArrayList<>();
//                 // 循环以前的栈，直到左括号为止，并把未遇到左括号之前的都加入一个list中。
//                 for (int j = 0; j < boolStack.size(); j++) {
//                     Object obj = boolStack.get(j);
//                     if (obj instanceof TagGroupExpressionRequest) {
//                         if (SYMBOL.getType().equals(((TagGroupExpressionRequest) obj).getType()) && OPEN_PAREN.getExpression().equals(((TagGroupExpressionRequest) obj).getSymbol())) {
//                             boolStack.pop();
//                             break;
//                         }
//                     }
//                     n++;
//                     list.add(obj);
//                 }
//                 // 清理栈
//                 for (int m = 0; m < n; m++) {
//                     boolStack.pop();
//                 }
//                 List<TagGroupExpressionRequest> convert = convert(list);
//                 List<TagGroupExpressionRequest> newConvert = new ArrayList<>();
//                 TagGroupExpressionRequest requestOpen = new TagGroupExpressionRequest();
//                 requestOpen.setSelects(null);
//                 requestOpen.setType(SYMBOL.getType());
//                 requestOpen.setSymbol(OPEN_PAREN.getExpression());
//                 TagGroupExpressionRequest requestClose = new TagGroupExpressionRequest();
//                 requestClose.setSelects(null);
//                 requestClose.setType(SYMBOL.getType());
//                 requestClose.setSymbol(CLOSE_PAREN.getExpression());
//                 newConvert.add(requestOpen);
//                 newConvert.addAll(convert);
//                 newConvert.add(requestClose);
//                 boolStack.push(newConvert);
//             } else {
//                 boolStack.push(expression);
//             }
//
//         }
//         List convert = convert(boolStack);
//         List newList = new ArrayList();
//         toAllObject(convert, newList);
//         return newList;
//     }
//
//     /**
//      * 递归展开所有对象
//      *
//      * @param convert
//      * @param newList
//      */
//     public void toAllObject(List convert, List newList) {
//         TagGroupExpressionRequest tagOpenExpression = new TagGroupExpressionRequest();
//         tagOpenExpression.setType(SYMBOL.getType());
//         tagOpenExpression.setSymbol(OPEN_PAREN.getExpression());
//         TagGroupExpressionRequest tagCloseExpression = new TagGroupExpressionRequest();
//         tagCloseExpression.setType(SYMBOL.getType());
//         tagCloseExpression.setSymbol(CLOSE_PAREN.getExpression());
//
//         for (Object o : convert) {
//             if (o instanceof List) {
//                 newList.add(tagOpenExpression);
//                 for (Object newo : (List) o) {
//                     if (newo instanceof List) {
//                         newList.add(tagOpenExpression);
//                         toAllObject((List) newo, newList);
//                         newList.add(tagCloseExpression);
//                     } else {
//                         newList.add(newo);
//                     }
//                 }
//                 newList.add(tagCloseExpression);
//             } else {
//                 newList.add(o);
//             }
//         }
//     }
//
//
//     /**
//      * 公式补括号逻辑
//      *
//      * @param list
//      * @return
//      */
//     public List convert(List list) {
//         List listObj = new ArrayList();
//         List newList = new ArrayList();
//         for (Object o : list) {
//             if (o instanceof TagGroupExpressionRequest) {
//                 if (!(SYMBOL.getType().equals(((TagGroupExpressionRequest) o).getType())
//                         && OPEN_PAREN.getExpression().equals(((TagGroupExpressionRequest) o).getSymbol()))) {
//                     newList.add(o);
//                 }
//             } else {
//                 newList.add(o);
//             }
//         }
//
//         for (Object obj : newList) {
//             if (obj instanceof TagGroupExpressionRequest) {
//                 if (SYMBOL.getType().equals(((TagGroupExpressionRequest) obj).getType())
//                         && (MINUS.getExpression().equals(((TagGroupExpressionRequest) obj).getSymbol())
//                         || INTERSECT.getExpression().equals(((TagGroupExpressionRequest) obj).getSymbol())
//                         || UNION.getExpression().equals(((TagGroupExpressionRequest) obj).getSymbol()))
//                 ) {
//                     listObj.add(obj);
//                 }
//             }
//         }
//         int i = listObj.size();
//         Collections.reverse(newList);
//         TagGroupExpressionRequest tagCloseExpression = new TagGroupExpressionRequest();
//         tagCloseExpression.setType(SYMBOL.getType());
//         tagCloseExpression.setSymbol(CLOSE_PAREN.getExpression());
//
//         TagGroupExpressionRequest tagOpenExpression = new TagGroupExpressionRequest();
//         tagOpenExpression.setType(SYMBOL.getType());
//         tagOpenExpression.setSymbol(OPEN_PAREN.getExpression());
//         for (int j = 0; j < i - 1; j++) {
//             newList.add(j, tagOpenExpression);
//         }
//         int f = i + 2;
//         for (int h = 1; h < i; h++) {
//             if (h == 1) {
//                 newList.add(f, tagCloseExpression);
//             } else {
//
//                 f += 3;
//                 newList.add(f, tagCloseExpression);
//             }
//         }
//         return newList;
//     }
//
//
//     /**
//      * 自内向外生成ES BoolQueryBuilder
//      *
//      * @param allList
//      * @return
//      */
//     public StringBuilder doSql(List<TagGroupExpressionRequest> allList) {
//         //符号栈
//         LinkedList<String> opraStack = new LinkedList<String>();
//         //括号栈跟实体栈
//         LinkedList boolStack = new LinkedList();
//
//         TagGroupExpressionRequest tagGroupRequest;
//         TagGroupExpressionRequest s;
//         List list;
//         for (int i = 0; i < allList.size(); i++) {
//             tagGroupRequest = allList.get(i);
//             s = tagGroupRequest;
//             if (INTERSECT.getExpression().equals(s.getSymbol()) || UNION.getExpression().equals(s.getSymbol()) || MINUS.getExpression().equals(s.getSymbol())) {
//                 // 符号入栈
//                 opraStack.push(s.getSymbol());
//             } else if (CLOSE_PAREN.getExpression().equals(s.getSymbol())) {
//                 // 如果碰到右括号
//                 int n = 0;
//                 list = new ArrayList<String>();
//                 for (int j = 0; j < boolStack.size(); j++) {
//                     // 循环以前的栈，直到左括号为止，并把未遇到左括号之前的都加入一个list中。
//                     Object obj2 = boolStack.get(j);
//                     if (obj2 instanceof TagGroupExpressionRequest) {
//                         TagGroupExpressionRequest tag = (TagGroupExpressionRequest) boolStack.get(j);
//                         if (SYMBOL.getType().equals(tag.getType())) {
//                             if (OPEN_PAREN.getExpression().equals(tag.getSymbol())) {
//                                 boolStack.pop();
//                                 break;
//                             }
//                         }
//                     }
//                     n++;
//                     list.add(obj2);
//                 }
//                 // 清理栈
//                 for (int m = 0; m < n; m++) {
//                     boolStack.pop();
//                 }
//                 // 运算后把左括号的内容加入到bool栈中
//                 boolStack.push(doSqlBySymbol(list, opraStack));
//             } else {
//                 boolStack.push(tagGroupRequest);
//             }
//         }
//
//         StringBuilder bools = doSqlBySymbol(boolStack, opraStack);
//         return bools;
//     }
//
//     /**
//      * 根据标签组跟交并差符号生成sql逻辑
//      *
//      * @param list
//      * @param opraStack
//      * @return
//      */
//     public StringBuilder doSqlBySymbol(List list,
//                                        LinkedList<String> opraStack) {
//         StringBuilder finalSql = new StringBuilder();
//         if (list.size() == 1) {
//             Object obj = list.get(0);
//             if (obj instanceof TagGroupExpressionRequest) {
//                 TagGroupExpressionRequest expressionRequest = (TagGroupExpressionRequest) obj;
//                 if (TAG_GROUP.getType().equals(expressionRequest.getType())) {
//                     //如果是标签表达式
//                     finalSql.append(doSingleSql(expressionRequest));
//                 }
//             } else if (obj instanceof StringBuilder) {
//                 finalSql.append(obj);
//             }
//
//         } else if (list.size() == 2) {
//             List<String> listSql = new ArrayList<>();
//             for (int i = 0; i < list.size(); i++) {
//                 Object obj = list.get(i);
//                 if (obj instanceof TagGroupExpressionRequest) {
//                     TagGroupExpressionRequest expressionRequest = (TagGroupExpressionRequest) obj;
//                     if (TAG_GROUP.getType().equals(expressionRequest.getType())) {
//                         //如果是标签表达式
//                         String doSingleSql = doSingleSql(expressionRequest);
//                         listSql.add(doSingleSql);
//                     }
//                 } else if (obj instanceof StringBuilder) {
//                     listSql.add(obj.toString());
//                 }
//             }
//             Map<String, Object> map = new HashMap<>(16);
//             map.put("sqlLeft", listSql.get(0));
//             map.put("sqlRight", listSql.get(1));
//             finalSql.append(getSqlUtil.getSql(map, opraStack.get(0).toLowerCase()));
//             opraStack.pop();
//         }
//         return finalSql;
//     }
//
//
//     /**
//      * 单个标签组生成sql
//      *
//      * @param expressionRequest
//      * @return
//      */
//     private String doSingleSql(TagGroupExpressionRequest expressionRequest) {
//         TagGroupExpressionRequest tagGroupExpressionRequest = JSON.parseObject(JSON.toJSONString(expressionRequest), TagGroupExpressionRequest.class);
//         String sql = StrUtil.EMPTY;
//         // 全局标签类型
//         Integer globalTagType = expressionRequest.getGlobalTagType();
//         Integer tagGroupId = tagGroupExpressionRequest.getTagGroupId();
//         List<TagGroupSelect> tagGroupSelectList = new ArrayList<>();
//         if (Objects.equals(tagRefreshConstants.getRangeDataTagFlag(), 0)) {
//             if (!org.springframework.util.StringUtils.isEmpty(expressionRequest.getDataRangeTagFlagInfo())) {
//                 List<TagGroupSelect> selectsTmp = JSONArray.parseArray(expressionRequest.getDataRangeTagFlagInfo(), TagGroupSelect.class);
//                 if (!CollectionUtils.isEmpty(selectsTmp)) {
//                     tagGroupSelectList.addAll(selectsTmp);
//                 }
//             }
//         }
//         if (tagGroupId == null) {
//             if (ALL_CHANNEL.equals(tagGroupExpressionRequest.getDataRange())) {
//                 TagGroupSelect select = Optional.of(tagGroupExpressionRequest)
//                         .map(TagGroupExpressionRequest::getSelects)
//                         .map(e -> e.get(0)).orElse(null);
//                 if (ObjectUtil.isNotNull(select)) {
//                     if (select.getValue() != null) {
//                         tagGroupExpressionRequest.getSelects().remove(0);
//                     }
//                 }
//             }
//             sql = doTagGroupParseToSql(tagGroupExpressionRequest);
//         } else {
//             TagGroup tagGroupById;
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tagGroupById = tagGroupCustomVipMapper.getTagGroupById(tagGroupId);
//             } else {
//                 tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//             }
//             if (tagGroupById == null) {
//                 throw new Http400Exception("tag_group_not_found", "标签组不存在");
//             }
//             Integer isInitData = tagGroupById.getIsInitData();
//             //判断是否自定义
//             if (tagGroupExpressionRequest.getTagGroupSource() == 1 || isInitData == 1) {
//
//                 if (tagGroupById.getDealStatus() != null && tagGroupById.getDealStatus() == 3) {
//                     throw new Http400Exception("tag_group_stop", "标签组：[" + tagGroupById.getTagGroupName() + "]已停止更新");
//                 }
//                 //自定义处理方式
//                 List<TagGroupSelect> selects = tagGroupExpressionRequest.getSelects();
//                 TagGroupSelect tagGroupSelect = selects.get(0);
//                 Object values = tagGroupSelect.getValue();
//                 List<Integer> listIds = new ArrayList<>();
//                 if (values instanceof List) {
//                     listIds = JSON.parseArray(JSON.toJSONString(values), Integer.class);
//                 } else {
//                     listIds.add(Integer.parseInt(String.valueOf(values)));
//                 }
//                 if (CollectionUtil.isEmpty(listIds)) {
//                     throw new Http400Exception("pleaseChoose", "请选择筛选项");
//                 }
//                 //获取分层的标签id
//                 List<Tag> tags;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     tags = tagCustomVipMapper.getByIds(listIds);
//                 } else {
//                     tags = tagCustomMapper.getByIds(listIds);
//                 }
//                 if (CollectionUtil.isEmpty(tags)) {
//                     throw new Http400Exception("chooseNotFound", "未获取到内部筛选项，请联系管理员！");
//                 }
//                 List<String> sqlList = new ArrayList<>();
//                 //加分层用union all 合并(并集)
//                 for (Tag tag : tags) {
//                     if (tag.getDealStatus() != null && tag.getDealStatus() == 4) {
//                         throw new Http400Exception("tag_stop", "标签：[" + tag.getTagName() + "]已停止更新");
//                     }
//                     String tagSql = tag.getSqlQuery();
//                     if (!CollectionUtils.isEmpty(tagGroupSelectList)) {
//                         String expression = tag.getExpression();
//                         List<TagGroupExpressionRequest> tagGroupExpressionRequests = JSONArray.parseArray(expression, TagGroupExpressionRequest.class);
//                         if (!CollectionUtils.isEmpty(tagGroupExpressionRequests)) {
//                             for (TagGroupExpressionRequest tagGroupExpression : tagGroupExpressionRequests) {
//                                 if (null != tagGroupExpression && Objects.equals(tagGroupExpression.getType(), TAG_GROUP.getType()) && !CollectionUtils.isEmpty(tagGroupExpression.getSelects())) {
//                                     tagGroupExpression.getSelects().addAll(tagGroupSelectList);
//                                     tagGroupExpression.setDataRangeTagFlagInfo(expressionRequest.getDataRangeTagFlagInfo());
//                                     if (null != tagGroupSelect.getStoreValue()) {
//                                         for (TagGroupSelect select : tagGroupExpression.getSelects()) {
//                                             select.setStoreValue(tagGroupSelect.getStoreValue());
//                                         }
//                                     }
//                                 }
//                             }
//                             List<TagGroupExpressionRequest> list = convertBrackets(tagGroupExpressionRequests);
//                             tagSql = doSql(list).toString();
//                         }
//                     } else {
//                         tagSql = "";
//                         //递归验证
//                         if (StrUtil.isNotBlank(tag.getExpression())) {
//                             List<TagGroupExpressionRequest> expressions = JSON.parseArray(tag.getExpression(), TagGroupExpressionRequest.class);
//                             if (CollectionUtil.isNotEmpty(expressions)) {
//                                 // 设置全局标签类型属性
//                                 expressions.forEach(logic -> logic.setGlobalTagType(globalTagType));
//                                 tagSql = buildQueryAllChannel(expressions, tagGroupExpressionRequest.getDataRange());
//                             }
//                         } else {
//                             if (isInitData == 1) {
//                                 tagSql = tag.getSqlQuery();
//                             }
//                         }
//                     }
//                     if (tags.indexOf(tag) == 0) {
//                         sql = tagSql;
//                     } else {
//                         Map<String, Object> map = new HashMap<>(16);
//                         map.put("sqlLeft", sql);
//                         map.put("sqlRight", tagSql);
//                         sql = getSqlUtil.getSql(map, UNION.getExpression().toLowerCase());
//                     }
//                     //偏好使用
//                     sqlList.add(tagSql);
//                 }
//                 if (tagGroupById.getCustomType() == 2) {
//                     //事件偏好属性,合并sql
//                     return mergeEventSql(sqlList);
//                 }
//             } else if (tagGroupExpressionRequest.getTagGroupSource() == 2 || tagGroupExpressionRequest.getTagGroupSource() == 0 || (tagGroupExpressionRequest.getTagGroupSource() == 0 && isInitData == 0)) {
//                 //复制一份expressionRequest
//                 //标签处理方式
//                 tagGroupExpressionRequest.getSelects().addAll(tagGroupSelectList);
//                 TagGroupExpressionRequest expressionRequestCopy = JSON.parseObject(JSON.toJSONString(tagGroupExpressionRequest), TagGroupExpressionRequest.class);
//                 TagGroupSelect select = Optional.of(tagGroupExpressionRequest)
//                         .map(TagGroupExpressionRequest::getSelects)
//                         .map(e -> e.get(0)).orElse(null);
//                 if (ObjectUtil.isNotNull(select)) {
//                     Object value = select.getValue();
//                     if (value != null) {
//                         if (value instanceof List) {
//                             List<String> valueStrs = CastUtils.cast(value);
//                             if (CollectionUtil.containsAny(valueStrs, channels)) {
//                                 if (ALL_CHANNEL.equals(tagGroupExpressionRequest.getDataRange())) {
//                                     //全渠道处理去除第一个数据范围
//                                     expressionRequestCopy.getSelects().remove(0);
//                                 }
//                             }
//                         } else {
//                             String valueStr = String.valueOf(value);
//                             if (channels.contains(valueStr)) {
//                                 if (ALL_CHANNEL.equals(tagGroupExpressionRequest.getDataRange())) {
//                                     //全渠道处理去除第一个数据范围
//                                     expressionRequestCopy.getSelects().remove(0);
//                                 }
//                             }
//                         }
//                     }
//                 }
//                 sql = doTagGroupParseToSql(expressionRequestCopy);
//             }
//         }
//         return sql;
//     }
//
//
//     /**
//      * 将渲染数据解析成sql
//      *
//      * @param expressionRequest
//      * @return
//      */
//     private String doTagGroupParseToSql(TagGroupExpressionRequest expressionRequest) {
//         List<TagGroupSelect> selects = expressionRequest.getSelects();
//         for (TagGroupSelect select : selects) {
//             if (Objects.equals(select.getAttributeId(), 1) && Objects.equals(select.getValue(), tagRefreshConstants.getAllChannelStr())) {
//                 select.setValue(expressionRequest.getChannelCode());
//             }
//         }
//         //selects中所有模板组成
//         List<String> parts = selects.stream().filter(e -> StrUtil.isNotBlank(e.getTemplatePart())).map(TagGroupSelect::getTemplatePart).distinct().collect(Collectors.toList());
//
//         //查询所有模板组成
//         List<TemplateMapping> mappings = tagCustomMapper.getTemplateMapping();
//
//         //Map<String, List<TemplateMapping>> mappings = mapping.stream().collect(Collectors.groupingBy(TemplateMapping::getTemplateCode));
//
//         String templateCode = StrUtil.EMPTY;
//
//         //寻找匹配模板(全部相等)
//         for (TemplateMapping templateMapping : mappings) {
//             String templatePart = templateMapping.getTemplatePart();
//             if (StrUtil.isNotBlank(templatePart)) {
//                 String[] split = templatePart.split("\\|");
//                 List<String> templateParts = Arrays.asList(split);
//                 if (parts.size() == templateParts.size() && parts.containsAll(templateParts)) {
//                     templateCode = templateMapping.getTemplateCode();
//                     break;
//                 }
//             }
//         }
//
//         if (StrUtil.isBlank(templateCode)) {
//             throw new Http400Exception("templateNotFound", "公式解析错误");
//         }
//
//         //根据模板解析
//         TemplateService model = templateFactory.getTemplate(templateCode);
//         if (ObjectUtil.isNull(model)) {
//             throw new Http400Exception("parseError", "解析错误");
//         }
//         return model.doParseTemplate(expressionRequest);
//     }
//
//
//     /**
//      * 标签数据同步
//      */
//     @Override
//     public void createTagGroupUserWithGp(Integer tagGroupId) {
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(tagGroupId);
//         Integer groupCount = 0;
//         Integer tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus();
//         Tag newTag;
//         String sql = StrUtil.EMPTY;
//         Integer tagId = null;
//         try {
//             TagGroup tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//             BeanUtils.copyProperties(tagGroupById, tagGroup);
//             if (ObjectUtil.isNull(tagGroupById)) {
//                 log.error("标签数据同步失败 ==> 未查询到标签组,tagGroupId:[{}]", tagGroupId);
//                 return;
//             }
//
//             tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//             tagGroupCustomMapper.updateTagGroupById(tagGroup);
//
//             //查询tag,进行跑数,对动态时间进行替换
//             List<Tag> tags = tagCustomMapper.getByTagGroupId(tagGroupId);
//             //判断计算状态
//
//             Integer isInitData = tagGroupById.getIsInitData();
//
//
//             tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//
//
//             if (CollectionUtil.isNotEmpty(tags)) {
//                 for (Tag tag : tags) {
//
//                     newTag = new Tag();
//                     tagId = tag.getId();
//
//                     Integer count = 0;
//                     if (tag.getDealStatus() != null && TagDelStatusEnum.CALCULATE_STOP.getDealStatus().equals(tag.getDealStatus())) {
//                         log.error("标签数据同步 ==> 已停止更新,tagId:[{}]", tag.getId());
//                         continue;
//                     }
//
//                     //更新分层状态
//                     newTag.setId(tag.getId());
//                     newTag.setDealStatus(TagDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//                     tagCustomMapper.updateTagById(newTag);
//                     Integer isEventObject = 0;
//                     if (isInitData == 1) {
//                         sql = OneIdTableUtils.initOneIdTable(tag.getSqlQuery(), tagRefreshConstants);
//                     } else {
//                         String expression = tag.getExpression();
//                         List<TagGroupExpressionRequest> requests = JSON.parseArray(expression, TagGroupExpressionRequest.class);
//                         sql = buildQueryAllChannel(requests, tagGroup.getDataRange());
//                         isEventObject = Optional.ofNullable(requests.get(0)).map(TagGroupExpressionRequest::getSelects).map(t -> t.get(t.size() - 1)).map(TagGroupSelect::getIsEventObject).orElse(0);
//                     }
//                     //判断是否被引用
//                     Integer tagRelationCount = tagCustomMapper.getTagRelationCount(tag.getId());
//                     count = userTagSqlMapper.getCount(sql);
//                     if (count == 0 && isEventObject == 1 && tagRelationCount == 0) {
//                         //删除分层并且跳过
//                         tagCustomMapper.physicalDeleteById(tag.getId());
//                         continue;
//                     }
//                     //累计标签组人数
//                     groupCount += count;
//
//                     //TODO 数据落表 >>>>>>>>>>>>
//                     //TODO
//                     //TODO 数据落表 <<<<<<<<<<<<
//
//                     //更新分层
//                     newTag.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//                     newTag.setLayerCount(count);
//
//                     newTag.setCountUpdateTime(new Date());
//                     tagCustomMapper.updateTagById(newTag);
//
//                     // 更新分层相关的逻辑
//                     try {
//                         // 处理联动逻辑
//                         TagRelationInfo tagRelationInfo = new TagRelationInfo();
//                         tagRelationInfo.setId(Long.valueOf(tag.getId()));
//                         tagRelationInfo.setType(TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                         tagRelationInfo.setDeleteFlag(0);
//                         // 只有数值为1的时候为自定义的，0,2都为系统的
//                         if (Objects.equals(tagGroupById.getTagGroupSource(), 1)) {
//                             // 非系统标签，需要确认级联关系
//                             // 系统标签直接更新被动的就行了，系统的需要
//                             Set<Integer> tagSet = new HashSet<>();
//                             List<TagGroupExpressionRequest> expressions = JSON.parseArray(tag.getExpression(), TagGroupExpressionRequest.class);
//                             getRelations(expressions, tagSet);
//                             tagRelationInfo.setTagIds(new ArrayList<>(tagSet));
//                         }
//                         rabbitTemplate.convertAndSend(TagConstants.RabbitMQConstants.TAG_RELATION_INFO_EXCHANGE, TagConstants.RabbitMQConstants.TAG_RELATION_INFO_ROUTING, JSONObject.toJSONString(tagRelationInfo));
//                     } catch (Exception e) {
//                         log.error("deal update error:" + JSON.toJSONString(tag), e);
//                     }
//                 }
//             }
//         } catch (Exception e) {
//             newTag = new Tag();
//             //更新分层状态
//             newTag.setId(tagId);
//             log.error("标签数据同步失败 ==> 组id：[{}],生成sql:[{}],异常：[{}]", tagGroupId, sql, e);
//             //更新标签分层状态为失败,如果是停止更新则更新为停止更新
//             if (e instanceof Http400Exception) {
//                 Http400Exception exception = CastUtils.cast(e);
//                 if (CommonConstant.TAG_STOP.equals(exception.getErrorCode())) {
//                     newTag.setDealStatus(TagDelStatusEnum.CALCULATE_STOP.getDealStatus());
//                     //更新标签组状态为失败
//                     tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_STOP.getDealStatus();
//                 } else {
//                     newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                     //更新标签组状态为失败
//                     tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_FAILURE.getDealStatus();
//                 }
//             } else {
//                 newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                 //更新标签组状态为失败
//                 tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_FAILURE.getDealStatus();
//             }
//
//             newTag.setCountUpdateTime(new Date());
//             tagCustomMapper.updateTagById(newTag);
//         }
//
//         //更新标签组状态为成功
//         if (TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus().equals(tagGroupStatus)) {
//             //失败不更新标签组人群更新时间跟数据
//             tagGroup.setLayerCount(groupCount);
//             tagGroup.setCountUpdateTime(new Date());
//         }
//         tagGroup.setDealStatus(tagGroupStatus);
//         /** 标签详情不在打
//          // 默认标签不进行计算
//          tagGroup.setTagDetailStatus(TagEnumConstants.TagDetailDealStatus.notcalculate.getKey());
//          // 低于一定数量的走逻辑，打成表信息
//          if (null != groupCount && groupCount < tagRefreshConstants.getTagDetailLimitCount()) {
//          // 标签改为计算中
//          tagGroup.setTagDetailStatus(TagEnumConstants.TagDetailDealStatus.calculating.getKey());
//          rabbitTemplate.convertAndSend(TagConstants.RabbitMQConstants.TAG_DETAIL_INFO_EXCHANGE, TagConstants.RabbitMQConstants.TAG_DETAIL_INFO_ROUTING, JSONObject.toJSONString(tagGroup));
//          }
//          */
//         tagGroupCustomMapper.updateTagGroupById(tagGroup);
//         log.info("标签数据同步完成,标签组:[{}]", tagGroupId);
//     }
//
//     @Resource
//     Executor tagExecutor;
//
//     @Override
//     public void createTagGroupUserWithGpNew(Integer tagGroupId) throws InterruptedException {
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(tagGroupId);
//         TagGroup tagGroupById = tagGroupCustomMapper.getTagGroupById(tagGroupId);
//         BeanUtils.copyProperties(tagGroupById, tagGroup);
//         if (ObjectUtil.isNull(tagGroupById)) {
//             log.error("标签数据同步失败 ==> 未查询到标签组,tagGroupId:[{}]", tagGroupId);
//             return;
//         }
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroupCustomMapper.updateTagGroupById(tagGroup);
//         Integer isNotLimit = tagGroupById.getIsNotLimit();
//         //查询tag,进行跑数,对动态时间进行替换
//         List<Tag> tags = tagCustomMapper.getByTagGroupId(tagGroupId);
//         //判断计算状态
//         Integer isInitData = tagGroupById.getIsInitData();
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//         if (CollectionUtil.isNotEmpty(tags)) {
//             Map<String, Integer> limitMap = new HashMap<>();
//             if (tagGroup.getCustomType() == 2) {
//                 //事件偏好属性
//                 //删除分层并且跳过
//                 limitMap = doNotLimit(tags.get(0).getSqlQuery());
//             }
//             //分批次
//             List<List<Tag>> split = CollUtil.split(tags, 300);
//             CountDownLatch noPackingLatch = new CountDownLatch(split.size());
//             for (List<Tag> tagList : split) {
//                 Map<String, Integer> finalLimitMap = limitMap;
//                 tagExecutor.execute(new Runnable() {
//                     @Override
//                     public void run() {
//                         try {
//                             for (Tag tag : tagList) {
//                                 try {
//                                     Tag newTag = new Tag();
//                                     Integer tagId = tag.getId();
//                                     String sql = StrUtil.EMPTY;
//                                     Integer count = 0;
//                                     if (tag.getDealStatus() != null && TagDelStatusEnum.CALCULATE_STOP.getDealStatus().equals(tag.getDealStatus())) {
//                                         log.error("标签数据同步 ==> 已停止更新,tagId:[{}]", tagId);
//                                         continue;
//                                     }
//                                     //更新分层状态
//                                     newTag.setId(tagId);
//                                     newTag.setDealStatus(TagDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//                                     tagCustomMapper.updateTagById(newTag);
//                                     if (isInitData == 1) {
//                                         sql = OneIdTableUtils.initOneIdTable(tag.getSqlQuery(), tagRefreshConstants);
//                                     } else {
//                                         String expression = tag.getExpression();
//                                         List<TagGroupExpressionRequest> requests = JSON.parseArray(expression, TagGroupExpressionRequest.class);
//                                         sql = buildQueryAllChannel(requests, tagGroup.getDataRange());
//                                         newTag.setSqlQuery(sql);
//                                     }
//                                     //判断是否被引用
//                                     Integer tagRelationCount = tagCustomMapper.getTagRelationCount(tag.getId());
//                                     if (tagGroup.getCustomType() != null && tagGroup.getCustomType() == 2) {
//                                         count = finalLimitMap.get(tag.getTagObjId()) == null ? 0 : finalLimitMap.get(tag.getTagObjId());
//                                     } else {
//                                         count = userTagSqlMapper.getCount(sql) == null ? 0 : userTagSqlMapper.getCount(sql);
//                                     }
//                                     if (count == 0 && isNotLimit != null && isNotLimit == 1 && tagRelationCount == 0) {
//                                         //时间偏好属性不限且跑数为0且未被引用的分层
//                                         //删除分层并且跳过
//                                         tagCustomMapper.physicalDeleteById(tag.getId());
//                                         continue;
//                                     }
//                                     //累计标签组人数
//                                     //groupCount += count;
//                                     //TODO 数据落表 >>>>>>>>>>>>
//                                     //TODO
//                                     //TODO 数据落表 <<<<<<<<<<<<
//                                     //更新分层
//                                     newTag.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//                                     newTag.setLayerCount(count);
//                                     newTag.setCountUpdateTime(new Date());
//                                     tagCustomMapper.updateTagById(newTag);
//                                     // 更新分层相关的逻辑
//                                     try {
//                                         // 处理联动逻辑
//                                         TagRelationInfo tagRelationInfo = new TagRelationInfo();
//                                         tagRelationInfo.setId(Long.valueOf(tag.getId()));
//                                         tagRelationInfo.setType(TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                                         tagRelationInfo.setDeleteFlag(0);
//                                         // 只有数值为1的时候为自定义的，0,2都为系统的
//                                         if (Objects.equals(tagGroupById.getTagGroupSource(), 1)) {
//                                             // 非系统标签，需要确认级联关系
//                                             // 系统标签直接更新被动的就行了，系统的需要
//                                             Set<Integer> tagSet = new HashSet<>();
//                                             List<TagGroupExpressionRequest> expressions = JSON.parseArray(tag.getExpression(), TagGroupExpressionRequest.class);
//                                             getRelations(expressions, tagSet);
//                                             tagRelationInfo.setTagIds(new ArrayList<>(tagSet));
//                                         }
//                                         rabbitTemplate.convertAndSend(TagConstants.RabbitMQConstants.TAG_RELATION_INFO_EXCHANGE, TagConstants.RabbitMQConstants.TAG_RELATION_INFO_ROUTING, JSONObject.toJSONString(tagRelationInfo));
//                                     } catch (Exception e) {
//                                         log.error("deal update error:" + JSON.toJSONString(tag), e);
//                                     }
//                                 } catch (Exception e) {
//                                     Integer tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus();
//                                     Tag newTag = new Tag();
//                                     newTag.setCountUpdateTime(new Date());
//                                     //更新分层状态
//                                     newTag.setId(tag.getId());
//                                     log.error("标签数据同步失败 ==> 组id：[{}],异常：[{}]", tagGroupId, e);
//                                     //更新标签分层状态为失败,如果是停止更新则更新为停止更新
//                                     if (e instanceof Http400Exception) {
//                                         Http400Exception exception = CastUtils.cast(e);
//                                         if (CommonConstant.TAG_STOP.equals(exception.getErrorCode())) {
//                                             newTag.setDealStatus(TagDelStatusEnum.CALCULATE_STOP.getDealStatus());
//                                         } else {
//                                             newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                                         }
//                                     } else {
//                                         newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                                     }
//                                     newTag.setDealStatus(tagGroupStatus);
//                                     tagCustomMapper.updateTagById(newTag);
//                                 }
//                             }
//                         } catch (Exception e) {
//                             log.error("标签数据同步失败 ==> 组id：[{}],异常：[{}]", tagGroupId, e);
//                         } finally {
//                             noPackingLatch.countDown();
//                         }
//                     }
//                 });
//             }
//             noPackingLatch.await();
//         }
//         Integer count = tagCustomMapper.getTagLayCount(tagGroupId);
//         tagGroup.setLayerCount(count);
//         tagGroup.setCountUpdateTime(new Date());
//         //查询所有分层判断状态
//         List<Integer> status = tagCustomMapper.getTagStatus(tagGroupId);
//         if (CollectionUtil.isNotEmpty(status)) {
//             List<Integer> disStatus = status.stream().distinct().collect(Collectors.toList());
//             if (CollectionUtil.isNotEmpty(disStatus)) {
//                 if (disStatus.size() == 1 && status.contains(TagDelStatusEnum.CALCULATE_STOP.getDealStatus())) {
//                     //所有分层计算停止则标签组状态更新为停止
//                     tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_STOP.getDealStatus());
//                 } else if (disStatus.size() == 1 && status.contains(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus())) {
//                     //所有分层计算失败则标签组状态更新为失败
//                     tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                 }
//             }
//         }
//         tagGroupCustomMapper.updateTagGroupById(tagGroup);
//         log.info("标签数据同步完成,标签组:[{}]", tagGroupId);
//     }
//
//     @Override
//     public void createVipTagGroupUserWithGpNew(Integer tagGroupId) throws InterruptedException {
//         TagGroup tagGroup = new TagGroup();
//         tagGroup.setId(tagGroupId);
//         TagGroup tagGroupById = tagGroupCustomVipMapper.getTagGroupById(tagGroupId);
//         BeanUtils.copyProperties(tagGroupById, tagGroup);
//         if (ObjectUtil.isNull(tagGroupById)) {
//             log.error("标签数据同步失败 ==> 未查询到标签组,tagGroupId:[{}]", tagGroupId);
//             return;
//         }
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//         tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//         Integer isNotLimit = tagGroupById.getIsNotLimit();
//         //查询tag,进行跑数,对动态时间进行替换
//         List<Tag> tags = tagCustomVipMapper.getByTagGroupId(tagGroupId);
//         //判断计算状态
//         Integer isInitData = tagGroupById.getIsInitData();
//         tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//         if (CollectionUtil.isNotEmpty(tags)) {
//             Map<String, Integer> limitMap = new HashMap<>();
//             if (tagGroup.getCustomType() == 2) {
//                 //事件偏好属性
//                 //删除分层并且跳过
//                 limitMap = doNotLimit(tags.get(0).getSqlQuery());
//             }
//             //分批次
//             List<List<Tag>> split = CollUtil.split(tags, 300);
//             CountDownLatch noPackingLatch = new CountDownLatch(split.size());
//             for (List<Tag> tagList : split) {
//                 Map<String, Integer> finalLimitMap = limitMap;
//                 tagExecutor.execute(() -> {
//                     try {
//                         for (Tag tag : tagList) {
//                             try {
//                                 Tag newTag = new Tag();
//                                 Integer tagId = tag.getId();
//                                 String sql = StrUtil.EMPTY;
//                                 Integer count = 0;
//                                 if (tag.getDealStatus() != null && TagDelStatusEnum.CALCULATE_STOP.getDealStatus().equals(tag.getDealStatus())) {
//                                     log.error("标签数据同步 ==> 已停止更新,tagId:[{}]", tagId);
//                                     continue;
//                                 }
//                                 //更新分层状态
//                                 newTag.setId(tagId);
//                                 newTag.setDealStatus(TagDelStatusEnum.IN_THE_CALCULATION.getDealStatus());
//                                 tagCustomVipMapper.updateTagById(newTag);
//                                 if (isInitData == 1) {
//                                     sql = OneIdTableUtils.initOneIdTable(tag.getSqlQuery(), tagRefreshConstants);
//                                 } else {
//                                     String expression = tag.getExpression();
//                                     List<TagGroupExpressionRequest> requests = JSON.parseArray(expression, TagGroupExpressionRequest.class);
//                                     // 设置全局标签类型标识
//                                     requests.forEach(logic -> logic.setGlobalTagType(VIP_TAG.getType()));
//                                     sql = buildQueryAllChannel(requests, tagGroup.getDataRange());
//                                     newTag.setSqlQuery(sql);
//                                 }
//                                 //判断是否被引用
//                                 Integer tagRelationCount = tagCustomVipMapper.getTagRelationCount(tag.getId());
//                                 if (tagGroup.getCustomType() != null && tagGroup.getCustomType() == 2) {
//                                     count = finalLimitMap.get(tag.getTagObjId()) == null ? 0 : finalLimitMap.get(tag.getTagObjId());
//                                 } else {
//                                     count = userTagSqlMapper.getCount(sql) == null ? 0 : userTagSqlMapper.getCount(sql);
//                                 }
//                                 if (count == 0 && isNotLimit != null && isNotLimit == 1 && tagRelationCount == 0) {
//                                     //时间偏好属性不限且跑数为0且未被引用的分层
//                                     //删除分层并且跳过
//                                     tagCustomVipMapper.physicalDeleteById(tag.getId());
//                                     continue;
//                                 }
//                                 //累计标签组人数
//                                 //groupCount += count;
//                                 //TODO 数据落表 >>>>>>>>>>>>
//                                 //TODO
//                                 //TODO 数据落表 <<<<<<<<<<<<
//                                 //更新分层
//                                 newTag.setDealStatus(TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus());
//                                 newTag.setLayerCount(count);
//                                 newTag.setCountUpdateTime(new Date());
//                                 tagCustomVipMapper.updateTagById(newTag);
//                                 // 更新分层相关的逻辑
//                                 try {
//                                     // 处理联动逻辑
//                                     TagRelationInfo tagRelationInfo = new TagRelationInfo();
//                                     tagRelationInfo.setId(Long.valueOf(tag.getId()));
//                                     tagRelationInfo.setType(TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//                                     tagRelationInfo.setDeleteFlag(0);
//                                     // 只有数值为1的时候为自定义的，0,2都为系统的
//                                     if (Objects.equals(tagGroupById.getTagGroupSource(), 1)) {
//                                         // 非系统标签，需要确认级联关系
//                                         // 系统标签直接更新被动的就行了，系统的需要
//                                         Set<Integer> tagSet = new HashSet<>();
//                                         List<TagGroupExpressionRequest> expressions = JSON.parseArray(tag.getExpression(), TagGroupExpressionRequest.class);
//                                         getRelations(expressions, tagSet);
//                                         tagRelationInfo.setTagIds(new ArrayList<>(tagSet));
//                                     }
//                                     rabbitTemplate.convertAndSend(VIP_TAG_RELATION_INFO_EXCHANGE, VIP_TAG_RELATION_INFO_ROUTING_KEY, JSONObject.toJSONString(tagRelationInfo));
//                                 } catch (Exception e) {
//                                     log.error("deal update error:" + JSON.toJSONString(tag), e);
//                                 }
//                             } catch (Exception e) {
//                                 Integer tagGroupStatus = TagGroupDelStatusEnum.CALCULATE_SUCCESS.getDealStatus();
//                                 Tag newTag = new Tag();
//                                 newTag.setCountUpdateTime(new Date());
//                                 //更新分层状态
//                                 newTag.setId(tag.getId());
//                                 log.error("标签数据同步失败 ==> 组id：[{}],异常：[{}]", tagGroupId, e);
//                                 //更新标签分层状态为失败,如果是停止更新则更新为停止更新
//                                 if (e instanceof Http400Exception) {
//                                     Http400Exception exception = CastUtils.cast(e);
//                                     if (CommonConstant.TAG_STOP.equals(exception.getErrorCode())) {
//                                         newTag.setDealStatus(TagDelStatusEnum.CALCULATE_STOP.getDealStatus());
//                                     } else {
//                                         newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                                     }
//                                 } else {
//                                     newTag.setDealStatus(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                                 }
//                                 newTag.setDealStatus(tagGroupStatus);
//                                 tagCustomVipMapper.updateTagById(newTag);
//                             }
//                         }
//                     } catch (Exception e) {
//                         log.error("标签数据同步失败 ==> 组id：[{}],异常：[{}]", tagGroupId, e);
//                     } finally {
//                         noPackingLatch.countDown();
//                     }
//                 });
//             }
//             noPackingLatch.await();
//         }
//         Integer count = tagCustomVipMapper.getTagLayCount(tagGroupId);
//         tagGroup.setLayerCount(count);
//         tagGroup.setCountUpdateTime(new Date());
//         //查询所有分层判断状态
//         List<Integer> status = tagCustomVipMapper.getTagStatus(tagGroupId);
//         if (CollectionUtil.isNotEmpty(status)) {
//             List<Integer> disStatus = status.stream().distinct().collect(Collectors.toList());
//             if (CollectionUtil.isNotEmpty(disStatus)) {
//                 if (disStatus.size() == 1 && status.contains(TagDelStatusEnum.CALCULATE_STOP.getDealStatus())) {
//                     //所有分层计算停止则标签组状态更新为停止
//                     tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_STOP.getDealStatus());
//                 } else if (disStatus.size() == 1 && status.contains(TagDelStatusEnum.CALCULATE_FAILURE.getDealStatus())) {
//                     //所有分层计算失败则标签组状态更新为失败
//                     tagGroup.setDealStatus(TagGroupDelStatusEnum.CALCULATE_FAILURE.getDealStatus());
//                 }
//             }
//         }
//         tagGroupCustomVipMapper.updateTagGroupById(tagGroup);
//         log.info("标签数据同步完成,标签组:[{}]", tagGroupId);
//     }
//
//     /**
//      * 获取引用信息
//      *
//      * @param expressions
//      * @return
//      */
//     private void getRelations(List<TagGroupExpressionRequest> expressions, Set<Integer> tagSet) {
//         // tag代表 分层，crowd代表人群 taggroup 代表标签组
//         if (!CollectionUtils.isEmpty(expressions)) {
//             for (TagGroupExpressionRequest expression : expressions) {
//                 //  标签组来源不为用户行为指标
//                 if (TAG_GROUP.getType().equals(expression.getType())) {
//                     if (null != expression.getTagGroupSource()) {
//                         // 为自定义标签
//                         if (Objects.equals(expression.getTagGroupSource(), 1)) {
//                             // 获取具体内容
//                             List<TagGroupSelect> selects = expression.getSelects();
//                             if (!CollectionUtils.isEmpty(selects)) {
//                                 TagGroupSelect tagGroupSelect = null;
//                                 for (TagGroupSelect select : selects) {
//                                     if (null != select && null != select.getAttributeId()) {
//                                         String attributeIdStr = select.getAttributeId().toString();
//                                         if (attributeIdStr.contains(expression.getTagGroupId().toString())) {
//                                             tagGroupSelect = select;
//                                             break;
//                                         }
//                                     }
//                                 }
//                                 if (null == tagGroupSelect) {
//                                     continue;
//                                 }
//                                 Object value = tagGroupSelect.getValue();
//                                 List<Integer> values = new ArrayList<>();
//                                 if (value instanceof List) {
//                                     values.addAll((List<Integer>) value);
//                                 } else if (value instanceof Integer) {
//                                     values.add((Integer) value);
//                                 }
//                                 tagSet.addAll(values);
//                             }
//                         }
//                         // 为系统标签
//                         if (Objects.equals(expression.getTagGroupSource(), 0)) {
//                             List<TagGroupSelect> selects = expression.getSelects();
//                             if (!CollectionUtils.isEmpty(selects)) {
//                                 boolean isValueList = false;
//                                 TagGroupSelect tagGroupSelect = null;
//                                 for (TagGroupSelect select : selects) {
//                                     if (null != select && Objects.equals(select.getTemplatePart(), tagRefreshConstants.getTagSystemTagInfo())) {
//                                         tagGroupSelect = select;
//                                     }
//                                     if (null != select && null != select.getAttributeId()) {
//                                         String attributeIdStr = select.getAttributeId().toString();
//                                         if (attributeIdStr.contains(expression.getTagGroupId().toString())) {
//                                             tagGroupSelect = select;
//                                             isValueList = true;
//                                             break;
//                                         }
//                                     }
//                                 }
//                                 if (null == tagGroupSelect) {
//                                     continue;
//                                 }
//                                 if (isValueList) {
//                                     Object value = tagGroupSelect.getValue();
//                                     List<Integer> values = new ArrayList<>();
//                                     if (value instanceof List) {
//                                         values.addAll((List<Integer>) value);
//                                     } else if (value instanceof Integer) {
//                                         values.add((Integer) value);
//                                     }
//                                     tagSet.addAll(values);
//                                 } else {
//                                     if (!StringUtils.isEmpty(String.valueOf(tagGroupSelect.getAttributeId()))) {
//                                         tagSet.add(Integer.valueOf(String.valueOf(tagGroupSelect.getAttributeId())));
//                                     }
//                                 }
//                             }
//                         }
//                         // 为微信标签
//                         if (Objects.equals(expression.getTagGroupSource(), 3)) {
//                             List<TagGroupSelect> selects = expression.getSelects();
//                             if (!CollectionUtils.isEmpty(selects)) {
//                                 TagGroupSelect tagGroupSelect = null;
//                                 for (TagGroupSelect select : selects) {
//                                     if (null != select && null != select.getAttributeId()) {
//                                         String attributeIdStr = select.getAttributeId().toString();
//                                         if (attributeIdStr.contains(expression.getTagGroupId().toString())) {
//                                             tagGroupSelect = select;
//                                             break;
//                                         }
//                                     }
//                                 }
//                                 if (null == tagGroupSelect) {
//                                     continue;
//                                 }
//                                 // todo 自定义标签value为list
//                                 Object value = tagGroupSelect.getValue();
//                                 List<Integer> values = new ArrayList<>();
//                                 if (value instanceof List) {
//                                     values.addAll((List<Integer>) value);
//                                 } else if (value instanceof Integer) {
//                                     values.add((Integer) value);
//                                 }
//                                 tagSet.addAll(values);
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//     }
//
//     @Override
//     public CommonResponse deleteTag(TagRequest request) {
//         Integer globalTagType = request.getGlobalTagType();
//         // 查询分层是否有被引用
//         TagRelationInfo tagRelationInfo;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagRelationInfo = tagRelationInfoVipDao.queryByIdType(Long.valueOf(request.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//         } else {
//             tagRelationInfo = tagRelationInfoDao.queryByIdType(Long.valueOf(request.getId()), TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//         }
//         if (null != tagRelationInfo) {
//             if (!CollectionUtils.isEmpty(tagRelationInfo.getPassiveCrowdIds()) || !CollectionUtils.isEmpty(tagRelationInfo.getPassiveTagIds())) {
//                 throw new Http400Exception("delete tag fail!", "分层被标签或者人群引用");
//             }
//         }
//         // tagCustomMapper.deleteById(request.getId());
//         // List<Integer> id = Arrays.asList(request.getId());
//         // //更新引用关系
//         // List<TagRelationInfo> tagRelationInfos = tagRelationInfoDao.batchQueryByIdTypes(id, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//         // updateTagRelation(id, tagRelationInfos, globalTagType);
//         // tagGroupRelationCustomMapper.deleteRelationByTagId(request.getId());
//         List<Integer> id = Arrays.asList(request.getId());
//         //更新引用关系
//         List<TagRelationInfo> tagRelationInfos;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             tagCustomVipMapper.deleteById(request.getId());
//             //更新引用关系
//             tagRelationInfos = tagRelationInfoVipDao.batchQueryByIdTypes(id, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//             //标签引用关系
//             tagGroupRelationCustomVipMapper.deleteRelationByTagId(request.getId());
//         } else {
//             tagCustomMapper.deleteById(request.getId());
//             //更新引用关系
//             tagRelationInfos = tagRelationInfoDao.batchQueryByIdTypes(id, TagEnumConstants.AttributeIdInfoEnum.TAG.getKey());
//             //标签引用关系
//             tagGroupRelationCustomMapper.deleteRelationByTagId(request.getId());
//         }
//         updateTagRelation(id, tagRelationInfos, globalTagType);
//         return new CommonResponse(true);
//     }
//
//     @Override
//     public List<TagResponse> getTagByGroupId(TagGroupIdRequest tagGroupIdRequest) {
//         List<TagResponse> responses = new ArrayList<>();
//         Integer globalTagType = tagGroupIdRequest.getGlobalTagType();
//         List<Tag> byTagGroupId;
//         if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//             // 会员标签
//             byTagGroupId = tagCustomVipMapper.getByTagGroupId(tagGroupIdRequest.getTagGroupId());
//         } else {
//             byTagGroupId = tagCustomMapper.getByTagGroupId(tagGroupIdRequest.getTagGroupId());
//         }
//         if (CollectionUtil.isNotEmpty(byTagGroupId)) {
//             TagResponse response;
//             for (Tag tag : byTagGroupId) {
//                 response = new TagResponse();
//                 response.setTagId(tag.getId());
//                 response.setTagName(tag.getTagName());
//                 responses.add(response);
//             }
//         }
//         return responses;
//     }
//
//     @Override
//     public TagSqlResponse getTagSql(TagGetSqlRequest request) {
//         Integer globalTagType = request.getGlobalTagType();
//         log.info("getTagSql globalTagType: {}", globalTagType);
//         TagSqlResponse response = new TagSqlResponse();
//         //选择的各个标签组
//         List<SelectRequest> selects = request.getSelects();
//         //处理分组，模拟交并差
//         if (CollectionUtil.isNotEmpty(selects)) {
//             String finalSql = "";
//             for (int i = 0; i < selects.size(); i++) {
//                 SelectRequest selectRequest = selects.get(i);
//                 Integer relation = selectRequest.getRelation();
//                 List<Integer> tagIds = selectRequest.getTagIds();
//                 //根据分层id获取分层，目前两种逻辑 1 不等于：相当于差集选中的分层的并集  2：包含 选择的分层做并集
//                 List<Tag> byTagIds;
//                 if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                     // 会员标签
//                     byTagIds = tagCustomVipMapper.getByTagIds(tagIds);
//                 } else {
//                     byTagIds = tagCustomMapper.getByTagIds(tagIds);
//                 }
//                 if (CollectionUtil.isNotEmpty(byTagIds)) {
//                     List<Integer> tagGroupIds = byTagIds.stream().map(Tag::getTagGroupId).distinct().collect(Collectors.toList());
//                     if (CollectionUtil.isEmpty(tagGroupIds) || tagGroupIds.size() > 1) {
//                         throw new Http400Exception("", "传参有误");
//                     }
//                     Integer tagGroupId = tagGroupIds.get(0);
//                     List<TagGroupChannel> tagGroupChannelByTagGroupId;
//                     if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                         // 会员标签
//                         tagGroupChannelByTagGroupId = tagGroupCustomVipMapper.getTagGroupChannelByTagGroupId(tagGroupId);
//                     } else {
//                         tagGroupChannelByTagGroupId = tagGroupCustomMapper.getTagGroupChannelByTagGroupId(tagGroupId);
//                     }
//                     if (CollectionUtil.isEmpty(tagGroupChannelByTagGroupId)) {
//                         throw new Http400Exception("", "传参有误");
//                     }
//                     String channelCode = tagGroupChannelByTagGroupId.get(0).getChannelCode();
//                     Map<String, Object> map;
//                     //生成单个标签组之中的union
//                     String groupFinalSql = "";
//                     for (int j = 0; j < byTagIds.size(); j++) {
//                         Tag tag = byTagIds.get(j);
//                         if (j >= 1) {
//                             map = new HashMap<>(16);
//                             map.put("sqlLeft", groupFinalSql);
//                             map.put("sqlRight", tag.getSqlQuery());
//                             groupFinalSql = getSqlUtil.getSql(map, "union");
//                         } else {
//                             groupFinalSql = tag.getSqlQuery();
//                         }
//                     }
//                     //如果是差集再排除一下，最终生成该标签组内的sql
//                     if (relation == 2) {
//                         //生成完以后如果是不等于，做leftjoin
//                         String baseLeftSql = "select user_acct from ads_user_oneid_action_tag_app_idm where prmry_busi_mdl_cd = " + "'" + channelCode + "'";
//                         map = new HashMap<>(16);
//                         map.put("sqlLeft", groupFinalSql);
//                         map.put("sqlRight", baseLeftSql);
//                         groupFinalSql = getSqlUtil.getSql(map, "minus");
//                     }
//                     //各个标签组之间的并集 交集，循环处理从第二个开始
//                     if (i > 0) {
//                         map = new HashMap<>(16);
//                         map.put("sqlLeft", finalSql);
//                         map.put("sqlRight", groupFinalSql);
//                         if (request.getSymbol() == 1) {
//                             finalSql = getSqlUtil.getSql(map, "intersect");
//                         } else if (request.getSymbol() == 2) {
//                             finalSql = getSqlUtil.getSql(map, "union");
//                         }
//                     } else {
//                         finalSql = groupFinalSql;
//                     }
//                 }
//                 String sql = finalSql.replaceAll("\\n", " ");
//                 response.setSql(sql);
//             }
//         }
//         return response;
//     }
//
//     @Override
//     public TagCountResponse getMemberTagUserCount(TagCountRequest request) {
//         request.setGlobalTagType(1);
//         return getUserTagUserCount(request);
//     }
//
//     @Override
//     public TagCountResponse getUserTagUserCount(TagCountRequest request) {
//         TagCountResponse response = new TagCountResponse();
//         TagGetSqlRequest getSqlRequest = new TagGetSqlRequest();
//         Integer globalTagType = request.getGlobalTagType();
//         List<Integer> tagGroupIds = request.getTagGroupIds();
//         List<Integer> tagIds = request.getTagIds();
//         getSqlRequest.setGlobalTagType(globalTagType);
//         List<Tag> tags;
//         if(CollectionUtil.isNotEmpty(tagGroupIds)){
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tags = tagCustomVipMapper.getByTagGroupIds(tagGroupIds);
//             } else {
//                 tags = tagGroupCustomMapper.getByTagGroupIds(tagGroupIds);
//             }
//         }else if (CollectionUtil.isNotEmpty(tagIds)) {
//             if (Objects.equals(VIP_TAG.getType(), globalTagType)) {
//                 // 会员标签
//                 tags = tagCustomVipMapper.getByTagIds(tagIds);
//             } else {
//                 tags = tagCustomMapper.getByTagIds(tagIds);
//             }
//         }else {
//             return response;
//         }
//
//
//
//         if (CollectionUtil.isNotEmpty(tags)) {
//             Map<Integer, List<Tag>> tagsMap = tags.stream().collect(Collectors.groupingBy(Tag::getTagGroupId));
//             List<SelectRequest> selects = new ArrayList<>();
//             for (Map.Entry<Integer, List<Tag>> sameGroupTagMap : tagsMap.entrySet()) {
//                 List<Tag> sameGroupTag = sameGroupTagMap.getValue();
//                 SelectRequest selectRequest = new SelectRequest();
//                 selectRequest.setRelation(1);
//                 selectRequest.setTagIds(sameGroupTag.stream().map(Tag::getId).collect(Collectors.toList()));
//                 selects.add(selectRequest);
//             }
//
//             getSqlRequest.setSelects(selects);
//             getSqlRequest.setSymbol(2);
//             TagSqlResponse tagSql = getTagSql(getSqlRequest);
//             String sql = tagSql.getSql();
//             Integer count = userTagSqlMapper.getUserCount(sql);
//             response.setCount(count);
//         }
//
//         return response;
//     }
//
//     /**
//      * 提交事务以后发送，没有事务的方法严禁使用！！！！！！！！！！！！！！！！！！！！！！！！
//      */
//     public void sendMsg(String queue, Object obj) {
//         try {
//             TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                 @Override
//                 public void afterCommit() {
//                     rabbitTemplate.convertAndSend(queue, obj.toString());
//                 }
//             });
//         } catch (Exception e) {
//             log.error("事务条件后发送消息失败，消息：[{}],异常:[{}]", obj, e);
//             throw new Http400Exception("sendMsg error", "操作失败");
//         }
//     }
//
//
//     //处理事件偏好不限
//     public Map<String, Integer> doNotLimit(String sql) {
//         Map<String, Integer> limitData = new HashMap<>();
//         String finalSql = doEventPreferenceSql(sql);
//         if (StrUtil.isNotBlank(finalSql)) {
//             List<DimCount> allLimitData = gpProductMapper.getLimitData(finalSql);
//             limitData = allLimitData.stream().collect(Collectors.toMap(DimCount::getDim, DimCount::getCount, (oldValue, newValue) -> newValue));
//         }
//         return limitData;
//     }
// }
//
