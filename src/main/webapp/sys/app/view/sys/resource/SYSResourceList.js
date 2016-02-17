Ext.define('EJ.view.sys.resource.SYSResourceList', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.sysResourceList',
			store : 'sys.resource.SYSResourceStore',
			title : '系统资源库',
			id : 'SYSResourceList',
			renderTo : Ext.getBody(),
			columnLines : true,
			initComponent : function() {
				this.columns = [{
							xtype : 'rownumberer'
						}, {
							header : '资源名称',
							dataIndex : 'name',
							flex : 1
						}, {
							header : 'URL',
							dataIndex : 'url',
							flex : 1
						}, {
							header : '最新操作者',
							dataIndex : 'endRegUser',
							flex : 1
						}, {
							header : '最新操作时间',
							dataIndex : 'endUpdateTime',
							renderer : Ext.util.Format
									.dateRenderer('Y-m-d H:i:s'),
							flex : 1
						}];
				this.bbar = Ext.create('Ext.PagingToolbar', {
							store : this.store,
							displayInfo : true
						});
				this.tbar = [{
							text : '新增资源',
							icon : './resources/img/add.png',
							action : 'add-resource-win'
						}, {
							text : '修改资源',
							icon : './resources/img/edit.png',
							disabled : true,
							action : 'show-editWin-win'
						}, {
							text : '删除资源',
							icon : ' ./resources/img/delete.png',
							disabled : true,
							action : 'delete-resource'
						}, '->', {
							xtype : 'searchForm',
							width : 300
						}];
				this.callParent(arguments);
				this.on('selectionchange', function(sm, selections) {
							this.down('button[action=delete-resource]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=show-editWin-win]')
									.setDisabled(!selections.length > 0);
						});
				this.getStore().load();
			}
		});