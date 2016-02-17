Ext.define('EJ.view.sys.roles.SYSRoleUsersTab', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.sysRoleUsersTab',
	title : '用户分配',
	id : 'RoleUser',
	store : 'sys.roles.SYSRoleUserStore',
	columnLines : true,
	initComponent : function() {
		this.selModel = Ext.create('Ext.selection.CheckboxModel');
		this.columns = [{
					header : '用户名',
					dataIndex : 'username',
					flex : 1
				}, {
					header : '公司名称',
					dataIndex : 'businessName',
					flex : 1
				}, {
					header : 'VIP',
					dataIndex : 'vip',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '否';
								break;
							case true :
								return '<img src="./resources/img/vip.png"></img>';
								break;
						}
					}
				}, {
					header : '锁状态',
					dataIndex : 'locked',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '<img src="./resources/img/key.png"></img>';
								break;
							case true :
								return '<img src="./resources/img/lock.png"></img>';
								break;
						}
					}
				}, {
					header : '启用状态',
					dataIndex : 'enabled',
					flex : 1,
					renderer : function(v) {
						switch (v) {
							case false :
								return '未启用';
								break;
							case true :
								return '<font color=green>启用</font>';
								break;
						}
					}
				}];
		this.bbar = Ext.create('Ext.PagingToolbar', {
					store : this.store,
					displayInfo : true
				});
		this.tbar = [{
					text : '新增资源',
					icon : './resources/img/add.png',
					action : 'show-config-role-users-win'
				}, {
					text : '删除资源',
					icon : './resources/img/delete.png',
					disabled : true,
					action : 'delete-role-config-users'
				}];
		this.callParent(arguments);
		this.on('selectionchange', function(sm, selections) {
					this.down('button[action=delete-role-config-users]')
							.setDisabled(!selections.length > 0);
				});
	}
});