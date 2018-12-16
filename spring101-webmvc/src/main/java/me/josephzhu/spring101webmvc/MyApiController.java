package me.josephzhu.spring101webmvc;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.josephzhu.spring101webmvc.framework.ApiController;
import me.josephzhu.spring101webmvc.framework.exception.ApiException;
import me.josephzhu.spring101webmvc.framework.filter.ApiFilter;
import me.josephzhu.spring101webmvc.framework.version.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@ApiController
@Slf4j
@ApiVersion("v1")
@ApiFilter(LoginCheck.class)
public class MyApiController {


    @GetMapping("hello")
    @ApiOperation("用来测试可以返回字符串")
    public String hello(@RequestParam("name")String name){
        return "hello " + name;
    }

    @PostMapping("item")
    @ApiOperation("用来测试自动的RequestBody")
    public MyItem setItem(MyItem myItem) {
        return myItem;
    }

    @ApiFilter(value = TestApiFilter1.class, order = 100)
    @ApiFilter(TestApiFilter2.class)
    @GetMapping("items")
    @ApiOperation("用来测试Filter")
    public List<MyItem> getItems() throws InterruptedException {
        Thread.sleep(1000);
        List<MyItem> myItems = new ArrayList<>();
        myItems.add(new MyItem("aa", 10));
        myItems.add(new MyItem("cc", 20));
        return myItems;
    }

    @ApiFilter(value = TestApiFilter1.class, order = 100)
    @ApiFilter(TestApiFilter2.class)
    @GetMapping("item/{id}")
    @ApiOperation("用来测试Filter中修改返回内容")
    public MyItem getItem(@PathVariable("id") String id) {
        return new MyItem("item" + id, 20);
    }

    @ApiFilter(TestApiFilter2.class)
    @GetMapping("item/{id}")
    @ApiVersion({"v2","v3"})
    @ApiOperation("用了测试版本")
    public MyItem getItemv2(@PathVariable("id") String id) {
        Integer i = null;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
        }
        if (i == null)
            throw new ApiException("1001", "商品ID只能是数字");
        if (i < 1)
            throw new RuntimeException("不合法的商品ID");

        return new MyItem("item" + id, 10);
    }
}
