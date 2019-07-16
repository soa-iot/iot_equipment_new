/** layui-v2.5.1 MIT License By https://www.layui.com */
;
layui.define("form", function(e) {
	"use strict";
	var i = layui.$, a = layui.form, n = {
		config : {},
		index : layui.atree ? layui.atree.index + 1e4 : 0,
		set : function(e) {
			var a = this;
			return a.config = i.extend({}, a.config, e), a
		},
		on : function(e, i) {
			return layui.onevent.call(this, l, e, i)
		}
	}, r = function() {
		var e = this, i = e.config;
		return {
			getChecked : function() {
				return e.getChecked()
			},
			setChecked : function(i) {
				return e.setChecked(i)
			},
			config : i
		}
	}, l = "tree", t = "layui-tree", s = "layui-tree-set", d = "layui-tree-iconClick", c = "layui-icon-addition", h = "layui-icon-subtraction", o = "layui-tree-entry", u = "layui-tree-main", p = "layui-tree-txt", f = "layui-tree-pack", y = "layui-tree-spread", v = "layui-tree-setLineShort", C = "layui-tree-showLine", m = "layui-tree-lineExtend", k = function(
			e) {
		var a = this;
		a.index = ++n.index, a.config = i.extend({}, a.config, n.config, e), a
				.render()
	};
	k.prototype.config = {
		data : [],
		showCheckbox : !1,
		showLine : !0,
		key : "id",
		checked : [],
		spread : [],
		accordion : !1,
		onlyIconControl : !1,
		isJump : !1,
		edit : !1,
		showSearch : !1,
		drag : !1,
		defaultNodeName : "未命名",
		emptyText : "暂无数据"
	}, k.prototype.render = function() {
		var e = this, a = e.config, n = i('<div class="layui-tree'
				+ (a.showCheckbox ? " layui-form" : "")
				+ (a.showLine ? " layui-tree-line" : "")
				+ '" lay-filter="LAY-tree-' + e.index + '"></div>');
		e.tree(n);
		var r = i(a.elem), l = r.next("." + t);
		return l[0] && l.remove(), a.showSearch
				&& n
						.prepend('<input type="text" class="layui-input layui-tree-search" placeholder="请输入关键字进行过滤">'), e.elem = n, e.emptyText = i('<div class="layui-tree-emptyText">'
				+ a.emptyText + "</div>"), r.html(e.elem), 0 == e.elem
				.find(".layui-tree-set").length ? void e.elem
				.append(e.emptyText) : (a.drag && e.drag(), a.showCheckbox
				&& e.renderForm("checkbox"), e.elem.find(".layui-tree-set")
				.each(function() {
					i(this).parent(".layui-tree-pack")[0]
							|| i(this).addClass("layui-tree-setHide"), !i(this)
							.next()[0]
							&& i(this).parents(".layui-tree-pack").eq(1)
									.hasClass("layui-tree-lineExtend")
							&& i(this).addClass(v), i(this).next()[0]
							|| i(this).parents(".layui-tree-set").eq(0).next()[0]
							|| i(this).addClass(v)
				}), void e.events())
	}, k.prototype.tree = function(e, a) {
		var n = this, r = n.config, l = a || r.data;
		layui.each(l, function(a, l) {
			function t(e) {
				return 0 != r.spread.length && i.inArray(e, r.spread) != -1
			}
			function d(e) {
				return 0 != r.checked.length && i.inArray(e, r.checked) != -1
			}
			var c = l.children && l.children.length > 0, h = i('<div class="layui-tree-pack" style="'
					+ (t(l[r.key]) ? "display: block;" : "") + '"></div>'), o = i([
					"<div "
							+ (r.key
									? 'data-key="' + (l[r.key] || "") + '"'
									: "") + ' class="layui-tree-set'
							+ (t(l[r.key]) ? " layui-tree-spread" : "")
							+ (d(l[r.key]) ? " layui-tree-checkedFirst" : "")
							+ '">',
					"<div " + (r.drag && !l.fixed ? 'draggable="true"' : "")
							+ ' class="layui-tree-entry">',
					'<div class="layui-tree-main layui-inline">', function() {
						return r.showLine
								? c
										? '<span class="layui-tree-iconClick layui-tree-icon"><i class="layui-icon '
												+ (t(l[r.key])
														? "layui-icon-subtraction"
														: "layui-icon-addition")
												+ '"></i></span>'
										: '<span class="layui-tree-iconClick"><i class="layui-icon layui-icon-file"></i></span>'
								: '<span class="layui-tree-iconClick"><i class="layui-tree-iconArrow '
										+ (c ? "" : "hide") + '"></i></span>'
					}(), function() {
						return r.showCheckbox
								? '<input type="checkbox" name="layuiTreeCheck" lay-skin="primary" '
										+ (l.disabled ? "disabled" : "") + ">"
								: ""
					}(), function() {
						return r.isJump && l.href
								? '<a href="' + l.href
										+ '" target="_blank" class="' + p
										+ '">' + (l.label || r.defaultNodeName)
										+ "</a>"
								: '<span class="' + p + '">'
										+ (l.label || r.defaultNodeName)
										+ "</span>"
					}(), "</div>", function() {
						if (!r.edit)
							return "";
						var e = {
							add : '<i class="layui-icon layui-icon-add-1"  data-type="add"></i>',
							update : '<i class="layui-icon layui-icon-edit" data-type="update"></i>',
							del : '<i class="layui-icon layui-icon-delete" data-type="del"></i>'
						}, i = ['<div class="layui-btn-group layui-tree-btnGroup">'];
						return r.edit === !0 && (r.edit = ["update", "del"]), "object" == typeof r.edit
								? (layui.each(r.edit, function(a, n) {
											i.push(e[n] || "")
										}), i.join("") + "</div>")
								: void 0
					}(), "</div></div>"].join(""));
			c && (o.append(h), n.tree(h, l.children)), e.append(o), o.prev("."
					+ s)[0]
					&& o.prev().children(".layui-tree-pack")
							.addClass("layui-tree-showLine"), c
					|| o.parent(".layui-tree-pack")
							.addClass("layui-tree-lineExtend"), n.spread(o, l), r.showCheckbox
					&& n.checkClick(o, l), r.edit && n.operate(o, l)
		})
	}, k.prototype.spread = function(e, i) {
		var a = this, n = a.config, r = e.children("." + o), l = r.children("."
				+ u), t = r.find("." + d), v = r.find("." + p), C = n.onlyIconControl
				? t
				: l, m = "";
		C.on("click", function(i) {
					var a = e.children("." + f), r = C.children(".layui-icon:not(.my-icon)")[0]
							? C.children(".layui-icon:not(.my-icon)")
							: C.find(".layui-tree-icon")
									.children(".layui-icon:not(.my-icon)");
					if (a[0]) {
						if (e.hasClass(y))
							e.removeClass(y), a.slideUp(200), r.removeClass(h)
									.addClass(c);
						else if (e.addClass(y), a.slideDown(200), r.addClass(h)
								.removeClass(c), n.accordion) {
							var l = e.siblings("." + s);
							l.removeClass(y), l.children("." + f).slideUp(200), l
									.find(".layui-tree-icon")
									.children(".layui-icon:not(.my-icon)").removeClass(h)
									.addClass(c)
						}
					} else
						m = "normal"
				}), v.on("click", function() {
					m = e.hasClass(y)
							? n.onlyIconControl ? "open" : "close"
							: n.onlyIconControl ? "close" : "open", n.click
							&& n.click({
										elem : e,
										state : m,
										data : i
									})
				})
	}, k.prototype.renderForm = function(e) {
		a.render(e, "LAY-tree-" + this.index)
	}, k.prototype.checkClick = function(e, a) {
		var n = this, r = n.config, l = e.children("." + o), t = l.children("."
				+ u);
		t.on("click", 'input[name="layuiTreeCheck"]+', function(l) {
					layui.stope(l);
					var t = i(this).prev(), d = t.prop("checked");
					if (!t.prop("disabled")) {
						if ("object" == typeof a.children || e.find("." + f)[0]) {
							var c = e.find("." + f)
									.find('input[name="layuiTreeCheck"]');
							c.each(function() {
										this.disabled || (this.checked = d)
									})
						}
						var h = function(e) {
							if (e.parents("." + s)[0]) {
								var i, a = e.parent("." + f), n = a.parent(), r = a
										.prev()
										.find('input[name="layuiTreeCheck"]');
								d ? r.prop("checked", d) : (a
										.find('input[name="layuiTreeCheck"]')
										.each(function() {
													this.checked && (i = !0)
												}), i || r.prop("checked", !1)), h(n)
							}
						};
						h(e), r.oncheck && r.oncheck({
									elem : e,
									checked : d,
									data : a
								}), n.renderForm("checkbox")
					}
				})
	}, k.prototype.operate = function(e, a) {
		var n = this, r = n.config, l = e.children("." + o), t = l.children("."
				+ u);
		l.children(".layui-tree-btnGroup").on("click", ".layui-icon",
				function(l) {
					layui.stope(l);
					var u = i(this).data("type"), k = e.children("." + f), g = {
						data : a,
						type : u,
						elem : e
					};
					if ("add" == u) {
						k[0]
								|| (r.showLine ? (t.find("." + d)
										.addClass("layui-tree-icon"), t
										.find("." + d).children(".layui-icon")
										.addClass(c)
										.removeClass("layui-icon-file")) : t
										.find(".layui-tree-iconArrow")
										.removeClass("hide"), e
										.append('<div class="layui-tree-pack"></div>'));
						var x = r.operate && r.operate(g), b = {};
						if (b.label = r.defaultNodeName, b[r.key] = x, n.tree(e
										.children("." + f), [b]), r.showLine)
							if (k[0])
								k.hasClass(m) || k.addClass(m), e.find("." + f)
										.each(function() {
											i(this).children("." + s).last()
													.addClass(v)
										}), k.children("." + s).last().prev()
										.hasClass(v) ? k.children("." + s)
										.last().prev().removeClass(v) : k
										.children("." + s).last()
										.removeClass(v), !e.parent("." + f)[0]
										&& e.next()[0]
										&& k.children("." + s).last()
												.removeClass(v);
							else {
								var w = e.siblings("." + s), T = 1, L = e
										.parent("." + f);
								layui.each(w, function(e, a) {
											i(a).children("." + f)[0]
													|| (T = 0)
										}), 1 == T ? (w.children("." + f)
										.addClass(C), w.children("." + f)
										.children("." + s).removeClass(v), e
										.children("." + f).addClass(C), L
										.removeClass(m), L.children("." + s)
										.last().children("." + f).children("."
												+ s).last().addClass(v)) : e
										.children("." + f).children("." + s)
										.addClass(v)
							}
						if (!r.showCheckbox)
							return;
						if (t.find('input[name="layuiTreeCheck"]')[0].checked) {
							var A = e.children("." + f).children("." + s)
									.last();
							A.find('input[name="layuiTreeCheck"]')[0].checked = !0
						}
						n.renderForm("checkbox")
					} else if ("update" == u) {
						var q = t.children("." + p).html();
						t.children("." + p).html(""), t
								.append('<input type="text" class="layui-inline layui-tree-editInput">'), t
								.children(".layui-tree-editInput").val(q)
								.focus();
						var N = function(e) {
							var i = e.val().trim();
							i = i ? i : r.defaultNodeName, e.remove(), t
									.children("." + p).html(i), g.data.label = i, r.operate
									&& r.operate(g)
						};
						t.children(".layui-tree-editInput").blur(function() {
									N(i(this))
								}), t.children(".layui-tree-editInput").on(
								"keydown", function(e) {
									13 === e.keyCode
											&& (e.preventDefault(), N(i(this)))
								})
					} else {
						if (r.operate && r.operate(g), g.status = "remove", !e
								.prev("." + s)[0]
								&& !e.next("." + s)[0] && !e.parent("." + f)[0])
							return e.remove(), void n.elem.append(n.emptyText);
						if (e.siblings("." + s).children("." + o)[0]) {
							if (r.showCheckbox) {
								var F = function(e) {
									if (e.parents("." + s)[0]) {
										var a = e.siblings("." + s)
												.children("." + o), r = e
												.parent("." + f).prev(), l = r
												.find('input[name="layuiTreeCheck"]')[0], t = 1, d = 0;
										0 == l.checked
												&& (a.each(function(e, a) {
													var n = i(a)
															.find('input[name="layuiTreeCheck"]')[0];
													0 != n.checked
															|| n.disabled
															|| (t = 0), n.disabled
															|| (d = 1)
												}), 1 == t
														&& 1 == d
														&& (l.checked = !0, n
																.renderForm("checkbox"), F(r
																.parent("." + s))))
									}
								};
								F(e)
							}
							if (r.showLine) {
								var w = e.siblings("." + s), T = 1, L = e
										.parent("." + f);
								layui.each(w, function(e, a) {
											i(a).children("." + f)[0]
													|| (T = 0)
										}), 1 == T ? (k[0]
										|| (L.removeClass(m), w.children("."
												+ f).addClass(C), w
												.children("." + f).children("."
														+ s).removeClass(v)), e
										.next()[0] ? L.children("." + s).last()
										.children("." + f).children("." + s)
										.last().addClass(v) : e.prev()
										.children("." + f).children("." + s)
										.last().addClass(v), e.next()[0]
										|| e.parents("." + s)[1]
										|| e.parents("." + s).eq(0).next()[0]
										|| e.prev("." + s).addClass(v)) : !e
										.next()[0]
										&& e.hasClass(v)
										&& e.prev().addClass(v)
							}
						} else {
							var I = e.parent("." + f).prev();
							if (r.showLine) {
								I.find("." + d).removeClass("layui-tree-icon"), I
										.find("." + d).children(".layui-icon")
										.removeClass(h)
										.addClass("layui-icon-file");
								var S = I.parents("." + f).eq(0);
								S.addClass(m), S.children("." + s).each(
										function() {
											i(this).children("." + f)
													.children("." + s).last()
													.addClass(v)
										})
							} else
								I.find(".layui-tree-iconArrow")
										.addClass("hide");
							e.parents("." + s).eq(0).removeClass(y), e
									.parent("." + f).remove()
						}
						e.remove()
					}
				})
	}, k.prototype.drag = function() {
		var e = this, a = e.config;
		e.elem.on("dragstart", "." + o, function() {
					var e = i(this).parent("." + s), n = e.parents("." + s)[0]
							? e.parents("." + s).eq(0)
							: "未找到父节点";
					a.dragstart && a.dragstart(e, n)
				}), e.elem.on("dragend", "." + o, function(n) {
			var n = n || event, r = n.clientY, l = i(this), t = l.parent("."
					+ s), u = t.height(), p = t.offset().top, k = e.elem
					.find("." + s), g = e.elem.height(), x = e.elem.offset().top, b = g
					+ x - 13, w = t.parents("." + s)[0], T = t.next()[0];
			if (w)
				var L = t.parent("." + f), A = t.parents("." + s).eq(0), q = A
						.parent("." + f), N = A.offset().top, F = t.siblings(), I = A
						.children("." + f).children("." + s).length;
			var S = function(n) {
				if (w
						|| T
						|| e.elem.children("." + s).last().children("." + f)
								.children("." + s).last().addClass(v), !w)
					return void t.removeClass("layui-tree-setHide");
				if (1 == I)
					a.showLine ? (n.find("." + d)
							.removeClass("layui-tree-icon"), n.find("." + d)
							.children(".layui-icon").removeClass(h)
							.addClass("layui-icon-file"), q.addClass(m), q
							.children("." + s).children("." + f).each(
									function() {
										i(this).children("." + s).last()
												.addClass(v)
									})) : n.find(".layui-tree-iconArrow")
							.addClass("hide"), n.children("." + f).remove(), n
							.removeClass(y);
				else {
					if (a.showLine) {
						var r = 1;
						layui.each(F, function(e, a) {
									i(a).children("." + f)[0] || (r = 0)
								}), 1 == r ? (t.children("." + f)[0]
								|| (L.removeClass(m), F.children("." + f)
										.addClass(C), F.children("." + f)
										.children("." + s).removeClass(v)), L
								.children("." + s).last().children("." + f)
								.children("." + s).last().addClass(v), T
								|| n.parents("." + s)[0] || n.next()[0]
								|| L.children("." + s).last().addClass(v)) : !T
								&& t.hasClass(v)
								&& L.children("." + s).last().addClass(v)
					}
					if (a.showCheckbox) {
						var l = function(a) {
							if (a) {
								if (!a.parents("." + s)[0])
									return
							} else if (!n[0])
								return;
							var r = a ? a.siblings().children("." + o) : F
									.children("." + o), t = a ? a.parent("."
									+ f).prev() : L.prev(), d = t
									.find('input[name="layuiTreeCheck"]')[0], c = 1, h = 0;
							0 == d.checked && (r.each(function(e, a) {
								var n = i(a)
										.find('input[name="layuiTreeCheck"]')[0];
								0 != n.checked || n.disabled || (c = 0), n.disabled
										|| (h = 1)
							}), 1 == c
									&& 1 == h
									&& (d.checked = !0, e
											.renderForm("checkbox"), l(t
											.parent("." + s)
											|| n)))
						};
						l()
					}
				}
			};
			k.each(function() {
				if (0 != i(this).height()) {
					if (r > p && r < p + u)
						return void(a.dragend && a.dragend("drag error"));
					if (1 == I && r > N && r < p + u)
						return void(a.dragend && a.dragend("drag error"));
					var n = i(this).offset().top;
					if (r > n && r < n + 15) {
						if (i(this).children("." + f)[0]
								|| (a.showLine
										? (i(this).find("." + d).eq(0)
												.addClass("layui-tree-icon"), i(this)
												.find("." + d).eq(0)
												.children(".layui-icon")
												.addClass(c)
												.removeClass("layui-icon-file"))
										: i(this).find(".layui-tree-iconArrow")
												.removeClass("hide"), i(this)
										.append('<div class="layui-tree-pack"></div>')), i(this)
								.children("." + f).append(t), S(A), a.showLine) {
							var l = i(this).children("." + f).children("." + s);
							if (t.children("." + f).children("." + s).last()
									.addClass(v), 1 == l.length) {
								var h = i(this).siblings("." + s), y = 1, k = i(this)
										.parent("." + f);
								layui.each(h, function(e, a) {
											i(a).children("." + f)[0]
													|| (y = 0)
										}), 1 == y
										? (h.children("." + f).addClass(C), h
												.children("." + f).children("."
														+ s).removeClass(v), i(this)
												.children("." + f).addClass(C), k
												.removeClass(m), k
												.children("." + s)
												.last()
												.children("." + f)
												.children("." + s)
												.last()
												.addClass(v)
												.removeClass("layui-tree-setHide"))
										: i(this)
												.children("." + f)
												.children("." + s)
												.addClass(v)
												.removeClass("layui-tree-setHide")
							} else
								t.prev("." + s).hasClass(v)
										? (t.prev("." + s).removeClass(v), t
												.addClass(v))
										: (t
												.removeClass("layui-tree-setLineShort layui-tree-setHide"), t
												.children("." + f)[0] ? t
												.prev("." + s)
												.children("." + f).children("."
														+ s).last()
												.removeClass(v) : t
												.siblings("." + s)
												.find("." + f).each(function() {
													i(this).children("." + s)
															.last().addClass(v)
												})), i(this).next()[0]
										|| t.addClass(v)
						}
						if (a.showCheckbox
								&& i(this).children("." + o)
										.find('input[name="layuiTreeCheck"]')[0].checked) {
							var g = t.children("." + o);
							g.find('input[name="layuiTreeCheck"]+').click()
						}
						return a.dragend
								&& a.dragend("drag success", t, i(this)), !1
					}
					if (r < n) {
						if (i(this).before(t), S(A), a.showLine) {
							var x = t.children("." + f), w = i(this)
									.parents("." + s).eq(0), T = w.children("."
									+ f).children("." + s).last();
							if (x[0]) {
								t.removeClass(v), x.children("." + s).last()
										.removeClass(v);
								var h = t.siblings("." + s), y = 1;
								layui.each(h, function(e, a) {
											i(a).children("." + f)[0]
													|| (y = 0)
										}), 1 == y ? w[0]
										&& (h.children("." + f).addClass(C), h
												.children("." + f).children("."
														+ s).removeClass(v), T
												.children("." + f).children("."
														+ s).last().addClass(v)
												.removeClass(C)) : t
										.children("." + f).children("." + s)
										.last().addClass(v), !w.parent("." + f)[0]
										&& w.next()[0] && T.removeClass(v)
							} else
								w.hasClass(m) || w.addClass(m), w.find("." + f)
										.each(function() {
											i(this).children("." + s).last()
													.addClass(v)
										});
							w[0]
									|| (t.addClass("layui-tree-setHide"), t
											.children("." + f)
											.children("." + s).last()
											.removeClass(v))
						}
						if (w[0]
								&& a.showCheckbox
								&& w.children("." + o)
										.find('input[name="layuiTreeCheck"]')[0].checked) {
							var g = t.children("." + o);
							g.find('input[name="layuiTreeCheck"]+').click()
						}
						return a.dragend
								&& a.dragend("拖拽成功，插入目标节点上方", t, i(this)), !1
					}
					if (r > b)
						return e.elem.children("." + s).last()
								.children("." + f).addClass(C), e.elem
								.append(t), S(A), t.prev().children("." + f)
								.children("." + s).last().removeClass(v), t
								.addClass("layui-tree-setHide"), t.children("."
								+ f).children("." + s).last().addClass(v), a.dragend
								&& a.dragend("拖拽成功，插入最外层节点", t, e.elem), !1
				}
			})
		})
	}, k.prototype.events = function() {
		var e = this, a = e.config, n = e.elem.find(".layui-tree-checkedFirst");
		layui.each(n, function(e, a) {
					i(a).children("." + o)
							.find('input[name="layuiTreeCheck"]+')
							.trigger("click")
				}), e.elem.find(".layui-tree-search").on("keyup", function() {
			var n = i(this), r = n.val(), l = n.nextAll(), t = [];
			l.find("." + p).each(function() {
				var e = i(this).parents("." + o);
				if (i(this).html().indexOf(r) != -1) {
					t.push(i(this).parent());
					var a = function(e) {
						e.addClass("layui-tree-searchShow"), e.parent("." + f)[0]
								&& a(e.parent("." + f).parent("." + s))
					};
					a(e.parent("." + s))
				}
			}), l.find("." + o).each(function() {
						var e = i(this).parent("." + s);
						e.hasClass("layui-tree-searchShow")
								|| e.addClass("layui-hide")
					}), 0 == l.find(".layui-tree-searchShow").length
					&& e.elem.append(e.emptyText), a.onsearch && a.onsearch({
						elem : t
					})
		}), e.elem.find(".layui-tree-search").on("keydown", function() {
			i(this).nextAll().find("." + o).each(function() {
						var e = i(this).parent("." + s);
						e.removeClass("layui-tree-searchShow layui-hide")
					}), i(".layui-tree-emptyText")[0]
					&& i(".layui-tree-emptyText").remove()
		})
	}, k.prototype.getChecked = function() {
		var e = this, a = e.config, n = [];
		return e.elem.find(".layui-form-checked").each(function() {
					var e = i(this).parents("." + s)[0];
					a.key ? n.push([e, i(e).data("key")]) : n.push(e)
				}), n
	}, k.prototype.setChecked = function(e) {
		var a = this;
		a.config;
		a.elem.find("." + s).each(function(a, n) {
			var r = i(this).data("key"), l = i(n).children("." + o)
					.find('input[name="layuiTreeCheck"]+');
			if ("number" == typeof e) {
				if (r == e)
					return l[0].checked || l.click(), !1
			} else
				i.inArray(r, e) != -1 && (l[0].checked || l.click())
		})
	}, n.render = function(e) {
		var i = new k(e);
		return r.call(i)
	}, e(l, n)
});