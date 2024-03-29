/* user-passport/1.0.0 login.pubSub.js md5:735cf06536dd1f99fefa3d9c4c756066 */
define("login/login.pubSub.js", [], function() {
    var c = {
        subscribe: function(a, b) {
            this._callbacks || (this._callbacks = {});
            return (this._callbacks[a] || (this._callbacks[a] = [])).push(b), this
        },
        publish: function() {
            var a = Array.prototype.slice.call(arguments, 0);
            var b = a.shift();
            var c, d, e, f;
            if (!(d = this._callbacks)) return this;
            if (!(c = this._callbacks[b])) return this;
            for (e = 0, f = c.length; f > e; e++) c[e].apply(this, a);
            return this
        },
        unsubscribe: function() {
            var a = Array.prototype.slice.call(arguments, 0);
            var b = a.shift();
            var c, d;
            return (d = this._callbacks) ? (c = this._callbacks[b]) ? (delete this._callbacks[b], this) : this : this
        }
    };
    return c
});