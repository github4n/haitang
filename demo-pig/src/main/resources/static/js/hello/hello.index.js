/**
 *
 * 路径：
 * 1. 绝对路径 http://localhost:/
 * 2. 相对路径 .  开头
 * 3. 根路径   / 开头
 * 4. base路径 默认通过 seajsnode 指定
 * Created by Administrator on 2017/8/1.
 */
seajs.use([
        "hello/hello.explorer.js",
    ],
    function (explorer) {
        explorer.init();
    }
);