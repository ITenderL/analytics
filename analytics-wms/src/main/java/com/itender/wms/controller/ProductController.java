package com.itender.wms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itender.wms.entity.Product;
import com.itender.wms.entity.Result;
import com.itender.wms.service.ProductService;
import com.itender.wms.vo.ProductQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author analytics
 * @since 2024-09-05
 */
@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Value("${file.upload-path}")
    private String uploadFilePath;

    @GetMapping("/list")
    public Result<List<Product>> list() {
        return Result.success(productService.list());
    }

    /**
     * 分页查询角色列表
     *
     * @param queryVO
     * @return
     */
    @PostMapping("/page")
    public Result<IPage<Product>> getProductByPage(@RequestBody ProductQueryVO queryVO) {
        // 返回数据
        return Result.success(productService.getProductByPage(queryVO));
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/add")
    public Result<String> addProduct(@RequestBody Product product) {
        log.info("添加商品信息：{}", product);
        product.setCreateBy(product.getUserId());
        productService.save(product);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/update")
    public Result<String> updateProduct(@RequestBody Product product) {
        product.setUpdateBy(product.getUserId());
        productService.updateProduct(product);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/updateUpDownState")
    public Result<String> updateUpDownStateById(@RequestBody Product product) {
        product.setUpdateBy(product.getUserId());
        productService.updateUpDownStateById(product);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteProduct(@PathVariable("id") Long id) {
        productService.removeById(id);
        return Result.success();
    }

    @PostMapping("/imageUpload")
    public Result<String> getById(@RequestBody MultipartFile file) {
        try {
            // 获取上传文件路径，拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadFilePath);
            // 文件上传到的目录路径的磁盘路径
            File uploadDirPath = uploadDirFile.getAbsoluteFile();
            // 上传文件名称
            String filename = file.getOriginalFilename();
            // 上传图片要保存的磁盘路径
            String uploadFileFullPath = uploadDirPath + "\\" + filename;
            // 上传图片
            file.transferTo(new File(uploadFileFullPath));
        } catch (IOException e) {
            return Result.error("上传文件失败");
        }
        return Result.success();
    }
}

