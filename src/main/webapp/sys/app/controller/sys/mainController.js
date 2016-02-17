Ext.define('EJ.controller.sys.mainController', {
			extend : 'Ext.app.Controller',
			init : function() {
				this.control({
							'menuTree' : {
								itemmousedown : this.changePage
							}
						});
			},
			changePage : function(view, record, item, index, e) {
				var title = record.get('text');
				var value = record.raw.value;
				var itme_id = title + '_' + value;
				window.TAB_ID = Ext.getCmp('TAB_ID');
				var old_tab = TAB_ID.getComponent(itme_id);
				if (value) {
					if (!old_tab) {
						var new_Tab = TAB_ID.add({
									title : title,
									closable : true,
									itemId : itme_id,
									xtype : value,
									listeners : {
										close : function(panel, eOpts) {
											TAB_ID.remove(panel);
											var a = TAB_ID.items.length - 1;
											TAB_ID.setActiveTab(a);
										}
									}
								});
						TAB_ID.setActiveTab(new_Tab);
					} else {
						TAB_ID.setActiveTab(old_tab);
					}
				}
			}
		});
/*
 * ajax请求异常处理器(全局，每次请求时调用)
 */
Ext.Ajax.on('requestexception', function(conn, response, options) {
			console.log(response.getResponseHeader("sessionstatus"));
			if (response.getResponseHeader("sessionstatus") == "timeout") {
				Ext.Msg.alert('提示', '会话超时，请重新登录！', function(btn, text) {
							if (btn === 'ok') {
								getRootWin().location.href = "../login";
							}
						});
			}
		}, this);