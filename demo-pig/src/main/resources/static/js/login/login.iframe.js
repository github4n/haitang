/* user-passport/1.0.0 login.iframe.js md5:6de5f7a88c324d337ea023b93764d8bc */
define("login/login.iframe.js", [], function() {
    var c = function(a) {
        this.opts = $.extend({
            top: 0,
            left: 0,
            width: 0,
            height: 0,
            coverEle: "body",
            iframeClass: "modallayer",
            zIndex: 3,
            callback: function() {}
        }, a),
        this.iframe = $('<iframe class="' + this.opts.iframeClass + '" marginwidth=0 marginheight=0 frameborder=0 scrolling="no" tabindex="-1" style="display:block;position:absolute;z-index:' + this.opts.zIndex + ';"/>')
    };
    return c.prototype = {
        constructor: c,
        create: function() {
            var a = $(this.opts.coverEle);
            var b = this.opts.top || a.position().top;
            var c = this.opts.left || a.position().left;
            var d = a.prop("offsetWidth");
            var e = a.prop("offsetHeight");
            this.iframe.css({
                top: b,
                left: c,
                width: d,
                height: e,
                display: "block"
            }), this.opts.callback(this.iframe)
        },
        remove: function() {
            this.iframe.hide()
        }
    }, c
});