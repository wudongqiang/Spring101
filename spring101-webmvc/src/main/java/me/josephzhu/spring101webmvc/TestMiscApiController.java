package me.josephzhu.spring101webmvc;

import io.swagger.annotations.ApiOperation;
import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.version.ApiVersion;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiController
@RequestMapping("misc")
@ApiVersion("")
public class TestMiscApiController {
    @PostMapping("item")
    @ApiOperation("用来测试自动的RequestBody和Validation")
    public MyItem setItem(MyItem myItem) {
        return myItem;
    }
}