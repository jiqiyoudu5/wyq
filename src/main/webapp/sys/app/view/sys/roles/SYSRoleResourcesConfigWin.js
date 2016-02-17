Ext.define('EJ.view.sys.roles.SYSRoleResourcesConfigWin', {
			extend : 'Ext.window.Window',
			title : '分配新资源',
			width : 500,
			height : 400,
			modal : true,
			border : false,
			alias : 'widget.sysRoleResourcesConfigWin',
			layout : 'fit',
			store : 'sys.roles.SYSRoleNoResourceStore',
			initComponent : function() {
				var sm = Ext.create('Ext.selection.CheckboxModel');
				this.items = [{
							xtype : 'grid',
							id : 'configRoleResorces',
							selModel : sm,
							store : this.store,
							columnLines : true,
							columns : [{
										text : '资源名称',
										dataIndex : 'name',
										flex : 1
									}, {
										text : 'URL',
										dataIndex : 'url',
										flex : 1
									}, {
										text : '说明',
										flex : 1,
										dataIndex : 'remark'
									}],
							bbar : Ext.create('Ext.PagingToolbar', {
										store : this.store,
										displayInfo : true
									})
						}];
				this.buttons = [{
							text : '确定',
							action : 'submit-config-role-resource'
						}, {
							text : '取消',
							scope : this,
							handler : this.close
						}];
				this.callParent(arguments);
			}

		});