Ext.define('EJ.view.sys.roles.SYSRoleList', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.sysRoleList',
			store : 'sys.roles.SYSRoleStore',
			id : 'SYSRoleList',
			columnLines : true,
			initComponent : function() {
				this.columns = [{
							header : '角色名称',
							dataIndex : 'name',
							flex : 1
						}, {
							header : 'CODE',
							dataIndex : 'code',
							flex : 1
						}];
				this.bbar = Ext.create('Ext.PagingToolbar', {
							store : this.store,
							displayInfo : true
						});
				this.tbar = [{
							text : '新增角色',
							icon : './resources/img/add.png',
							action : 'add-role-win'
						}, {
							text : '修改角色',
							icon : './resources/img/edit.png',
							disabled : true,
							action : 'show-editRoleWin-win'
						}, {
							text : '删除角色',
							icon : './resources/img/delete.png',
							disabled : true,
							action : 'delete-role'
						}, {
							text : '启用配置',
							icon : './resources/img/saved.png',
							action : 'using-new-config-role-resource'
						}];
				this.callParent(arguments);
				this.on('selectionchange', function(sm, selections) {
							this.down('button[action=delete-role]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=show-editRoleWin-win]')
									.setDisabled(!selections.length > 0);
						});
				this.getStore().load();
			}
		});