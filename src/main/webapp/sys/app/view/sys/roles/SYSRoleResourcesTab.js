Ext.define('EJ.view.sys.roles.SYSRoleResourcesTab', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.sysRoleResourcesTab',
			title : '资源分配',
			id : 'SYSRoleResourcesTab',
			store : 'sys.roles.SYSRoleResourceStore',
			columnLines : true,
			initComponent : function() {
				this.selModel = Ext.create('Ext.selection.CheckboxModel');
				this.columns = [{
							header : '资源名称',
							dataIndex : 'name',
							flex : 1
						}, {
							header : 'URL',
							dataIndex : 'url',
							flex : 1
						}, {
							header : '备注',
							dataIndex : 'remark',
							flex : 1
						}];
				this.bbar = Ext.create('Ext.PagingToolbar', {
							store : this.store,
							displayInfo : true
						});
				this.tbar = [{
							text : '配置资源',
							icon : './resources/img/add.png',
							action : 'show-config-role-resource-win'
						}, {
							text : '删除资源',
							icon : './resources/img/delete.png',
							disabled : true,
							action : 'delete-config-role-resource'
						}];
				this.callParent(arguments);
				this.on('selectionchange', function(sm, selections) {
							this
									.down('button[action=delete-config-role-resource]')
									.setDisabled(!selections.length > 0);
						});
			}
		});