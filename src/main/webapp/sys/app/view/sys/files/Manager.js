Ext.define('EJ.view.sys.files.Manager', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.filesManager',
			layout : 'fit',
			title : '附件库管理',
			initComponent : function() {
				this.tbar = [{
							text : '上传文件',
							icon : './resources/img/add.png',
							action : 'open_uploadWin'
						}, {
							text : 'Download',
							action : 'download',
							disabled : true,
							icon : './resources/img/download.png'
						}, {
							text : 'Delete',
							action : 'delete',
							disabled : true,
							icon : './resources/img/delete.png'
						}, {
							text : '文件审核',
							disabled : true,
							xtype : 'splitbutton',
							icon : './resources/img/edit.png',
							reorderable : false,
							menu : [{
										text : '通过',
										icon : './resources/img/saved.png',
										action : 'shzt-1'
									}, {
										text : '禁用',
										icon : './resources/img/stop.png',
										action : 'shzt-0'
									}, {
										text : '待审',
										icon : './resources/img/wait.png',
										action : 'shzt-2'
									}],
							action : 'find-users'
						}, {
							text : '图片管理',
							action : 'imgAction',
							icon : './resources/img/folder-images.png'
						}, {
							text : '显示全部',
							action : 'lookAll'
						}, '->', '文件后缀:', {
							xtype : "combo",
							name : 'postfix',
							displayField : 'text',// 显示值
							valueField : 'text',// 显示值在Store中对应的id值
							editable : true,
							store : 'sys.file.SYSDicFileHZ',
							queryMode : 'remote',// 远程加载数据
							queryParam : 'text',// 过滤条件
							minChars : 1,// 录入一个字符加远程过滤
							typeAhead : true,// 列出符合条件字符
							forceSelection : true,// 必须是列表中的才可以选
							width : 100
						}, '所属分类:', {
							xtype : "combo",
							name : 'type',
							displayField : 'text',// 显示值
							valueField : 'id',// 显示值在Store中对应的id值
							editable : true,
							store : 'sys.file.SYSDicFileType',
							queryMode : 'remote',// 远程加载数据
							queryParam : 'text',// 过滤条件
							minChars : 1,// 录入一个字符加远程过滤
							typeAhead : true,// 列出符合条件字符
							forceSelection : true,// 必须是列表中的才可以选
							width : 100
						}, '审核状态:', {
							xtype : "combo",
							editable : false,
							displayField : "text",
							valueField : "id",
							store : new Ext.data.SimpleStore({
										fields : ['id', 'text'],
										data : [['1', '通过'], ['0', '禁用'],
												['2', '待审']]
									}),
							name : "shzt",
							width : 100
						}, '上传时间:', {
							xtype : "datefield",
							format : "Y-m-d",
							editable : false,
							name : "beginTime",
							width : 100
						}, '至', {
							xtype : "datefield",
							format : "Y-m-d",
							editable : false,
							name : "endTime",
							width : 100
						}, {
							icon : './resources/img/serchMenu.png',
							text : '查询',
							action : "ok"
						}, {
							text : "重置",
							icon : './resources/img/refresh.png',
							action : "reset"
						}];
				this.items = [{
					xtype : 'fileList',
					listeners : {
						scope : this,
						selectionchange : function(ms, selections) {
							this.down('button[action=download]')
									.setDisabled(!selections.length > 0);
							this.down('button[action=delete]')
									.setDisabled(!selections.length > 0);
							this.down('splitbutton')
									.setDisabled(!selections.length > 0);
						}
					}
				}]
				this.callOverridden(arguments);
			}
		});