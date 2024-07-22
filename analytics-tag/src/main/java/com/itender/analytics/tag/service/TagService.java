package com.itender.analytics.tag.service;


import java.util.List;

public interface TagService {


    // /**
    //  * 查询渲染
    //  *
    //  * @param request
    //  * @return
    //  */
    // List<AttributeResponse> getTagRender(TagRenderRequest request);
    //
    // /**
    //  * 交并差公式校验
    //  *
    //  * @param requests
    //  * @return
    //  */
    // CommonResponse checkExpression(TagExpressionRequest requests);
    //
    // /**
    //  * 预估人数
    //  *
    //  * @param requests
    //  * @return
    //  */
    // TagCountResponse getProbablyCount(TagExpressionRequest requests);
    //
    //
    // /**
    //  * 创建
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse createTagGroup(CreateTagGroupRequest request);
    //
    //
    // /**
    //  * 创建时间偏好属性标签组
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse createEventTagGroup(CreateEventTagGroupRequest request);
    //
    // /**
    //  * 延迟创建分层
    //  *
    //  * @param delayCreateEventTagRequest
    //  */
    // void delayCreateEventTag(DelayEventTagRequest delayCreateEventTagRequest);
    //
    // /**
    //  * 查询详情
    //  *
    //  * @param request
    //  * @return
    //  */
    // TagGroupDetailsResponse getTagGroupDetail(TagGroupDetailRequest request);
    //
    // /**
    //  * 查询偏好详情
    //  *
    //  * @param request
    //  * @return
    //  */
    // TagGroupDetailsResponse getEventTagGroupDetail(TagGroupDetailRequest request);
    //
    // /**
    //  * 编辑
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse editTagGroup(EditTagGroupRequest request);
    //
    // /**
    //  * 编辑事件偏好
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse editEventTagGroup(EditEventTagGroupRequest request);
    //
    //
    // /**
    //  * 延迟编辑
    //  *
    //  * @param delayEditEventTagRequest
    //  */
    // void delayEventTagEdit(DelayEventTagRequest delayEditEventTagRequest);
    //
    // /**
    //  * 删除标签组
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse deleteTagGroup(TagGroupIdRequest request);
    //
    // /**
    //  * 查询同一渠道名称是否重复
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse checkGroupName(TagGroupNameRequest request);
    //
    // /**
    //  * 全量同步
    //  *
    //  * @return
    //  */
    // CommonResponse syncAllTagGroup();
    //
    // /**
    //  * 全量同步会员标签
    //  * @return
    //  */
    // CommonResponse syncAllVipTagGroup();
    //
    // CommonResponse syncAnyTagGroup(TagGroupIdsRequest request);
    //
    // /**
    //  * 同步
    //  *
    //  * @param tagGroupId
    //  */
    // void createTagGroupUserWithGp(Integer tagGroupId);
    //
    // void createTagGroupUserWithGpNew(Integer tagGroupId) throws InterruptedException;
    //
    // /**
    //  * 会员标签
    //  *
    //  * @param tagGroupId
    //  * @throws InterruptedException
    //  */
    // void createVipTagGroupUserWithGpNew(Integer tagGroupId) throws InterruptedException;
    //
    // /**
    //  * 删除分层的逻辑
    //  *
    //  * @param request
    //  * @return
    //  */
    // CommonResponse deleteTag(TagRequest request);
    //
    // /**
    //  * 获取标签分层
    //  *
    //  * @param tagGroupIdRequest
    //  * @return
    //  */
    // List<TagResponse> getTagByGroupId(TagGroupIdRequest tagGroupIdRequest);
    //
    //
    // /**
    //  * 根据分层id获取最终sql(运营看板使用)
    //  *
    //  * @param request
    //  * @return
    //  */
    // TagSqlResponse getTagSql(TagGetSqlRequest request);
    //
    // /**
    //  * 根据标签组获取人数
    //  * @param request
    //  * @return
    //  */
    // TagCountResponse getMemberTagUserCount(TagCountRequest request);
    //
    // /**
    //  * 根据标签组获取人数
    //  * @param request
    //  * @return
    //  */
    // TagCountResponse getUserTagUserCount(TagCountRequest request);
}
