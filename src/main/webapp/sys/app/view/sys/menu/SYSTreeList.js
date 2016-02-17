Ext.define('EJ.view.sys.menu.SYSTreeList', {
			extend : 'Ext.tree.Panel',
			alias : 'widget.sysTreeList',
			store : 'sys.menu.SYSConfigMenuTreeStore',
			title : '系统菜单库',
			id : 'SYSTreeList',
			columnLines : true,
			rootVisible : false,
			autoScroll : true,
			columns : [{
						xtype : 'treecolumn',
						text : '菜单名称',
						flex : 2,
						dataIndex : 'text'
					}, {
						text : '菜单说明',
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
					}, {
						text : '默认展开',
						width : 75,
						dataIndex : 'expanded',
						renderer : function(v) {
							if (v) {
								return '<font color=blue>是</font>';
							} else {
								return '否';
							}
						}
					}, {
						text : '排序',
						dataIndex : 'orderValue'
					}],
			initComponent : function() {
				this.tbar = [{
							text : '新增菜单',
							icon : './resources/img/add.png',
							disabled : true,
							action : 'add-menu-win'
						}, {
							text : '修改菜单',
							icon : './resources/img/edit.png',
							disabled : true,
							action : 'show-editMenu-win'
						}, {
							text : '删除菜单',
							icon : './resources/img/delete.png',
							disabled : true,
							action : 'delete-menu'
						}];
				this.callParent(arguments);
				this.on('selectionchange', function(sm, selections) {
							this.down('button[action=delete-menu]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=show-editMenu-win]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=add-menu-win]')
									.setDisabled(!selections.length > 0);
						});
			}
		});