Ext.define('EJ.view.sys.user.RemoveUserRolesWin', {
			extend : 'Ext.window.Window',
			alias : 'widget.removeUserRolesWin',
			title : '移除用户权限',
			height : 150,
			width : 300,
			modal : true,
			layout : 'fit',
			initComponent : function() {
				this.items = {
					xtype : 'form',
					border : false,
					layout : {
						align : 'middle',
						pack : 'center',
						type : 'hbox'
					},
					// buttonAlign:'center',
					items : [{
								xtype : 'combo',
								fieldLabel : '选择移除的权限',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								queryMode : 'local',
								multiSelect : true,
								allowBlank : false,
								store : 'sys.roles.SYSUserRoleStore'
							}],
					buttons : [{
								text : '提交',
								formBind : true,
								action : 'remove-user-roles'
							}, {
								text : '取消',
								scope : this,
								handler : this.close
							}]
				};
				this.callParent(arguments);
			}
		});