Ext.define('EJ.view.sys.user.RegistUserWin', {
			extend : 'Ext.window.Window',
			alias : 'widget.registUserWin',
			title : '注册新用户',
			width : 500,
			modal : true,
			resizable : false,
			initComponent : function() {
				this.items = {
					xtype : 'registUserForm',
					buttons : [{
								text : '提交',
								formBind : true,
								action : 'reg-user'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
						}]
				};
				this.callParent(arguments);
			}
		});