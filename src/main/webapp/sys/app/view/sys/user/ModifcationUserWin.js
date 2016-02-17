Ext.define('EJ.view.sys.user.ModifcationUserWin', {
			extend : 'Ext.window.Window',
			alias : 'widget.modifcationUserWin',
			title : '用户详细信息',
			layout : 'fit',
			width : 500,
			modal : true,
			resizable : false,
			autoShow : true,
			initComponent : function() {
				this.items = [{
							xtype : 'form',
							border : false,
							// bodyPadding : '5 5 0',
							items : [{
										xtype : 'modifcationUserForm',
										buttons : [{
													text : '提交',
													formBind : true,
													action : 'udpate-user'
												}, {
													text : '取消',
													scope : this,
													handler : this.close
											}]
									}]

						}];
				this.callParent(arguments);
			}
		});