Ext.define('EJ.view.sys.dictionary.SYSDicList', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.sysDicList',
			store : 'sys.dictionary.SYSDicStore',
			id : 'SYSDicList',
			columnLines : true,
			initComponent : function() {
				this.selModel = Ext.create('Ext.selection.CheckboxModel');
				this.columns = [{
							xtype : 'rownumberer'
						}, {
							header : '字典名称',
							dataIndex : 'text',
							flex : 1
						}, {
							header : '字典类型',
							dataIndex : 'type',
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
							text : '新增类别',
							icon : './resources/img/add.png',
							action : 'show_add_win'
						}, {
							text : '修改类别',
							icon : './resources/img/edit.png',
							disabled : true,
							action : 'show_update_win'
						}, {
							text : '删除类别',
							icon : './resources/img/delete.png',
							disabled : true,
							action : 'delete'
						}, '->', '关键词检索:', {
							xtype : 'textfield',
							name : 'keyWord',
							hideLabel : true,
							emptyText : '请输入关键词',
							width : 130
						}, {
							icon : './resources/img/serchMenu.png',
							text : '搜',
							action : 'search_byKeyWord'
						}];
				this.callParent(arguments);
				this.on('selectionchange', function(sm, selections) {
							this.down('button[action=delete]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=show_update_win]')
									.setDisabled(!selections.length > 0);
						});
			}
		});