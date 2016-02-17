Ext.define('EJ.view.sys.roles.SYSRoleMenuTab', {
			extend : 'Ext.tree.Panel',
			alias : 'widget.sysRoleMenuTab',
			title : '菜单分配',
			id : 'RoleMenuTab',
			columnLines : true,
			rootVisible : false,
			rowLines : true,
			store : 'sys.menu.SYSConfigMenuTreeStore',
			columns : [{
						xtype : 'treecolumn',
						text : '菜单名称',
						flex : 2,
						dataIndex : 'text'
					}, {
						text : '说明',
						flex : 1,
						dataIndex : 'remark'
					}, {
						text : '菜单指向',
						flex : 1,
						dataIndex : 'value'
					}, {
						text : '菜单类型',
						width : 75,
						dataIndex : 'leaf',
						renderer : function(v) {
							if (v) {
								return '<font color=blue>子节点</font>';
							} else {
								return '<font color=red>根节点</font>';
							}
						}
					}],
			initComponent : function() {
				this.selModel = Ext.create('Ext.selection.CheckboxModel');
				this.tbar = ['->', {
							text : '保存配置',
							action : 'save-config-role-menu',
							icon : './resources/img/disk.png'
						}];
				this.callParent(arguments);
			}
		});