Ext.define("EJ.view.sys.files.List", {
	extend : 'Ext.grid.Panel',
	alias : 'widget.fileList',
	id : 'filesList',
	layout : 'fit',
	store : 'sys.file.SYSFileStore',
	columnLines : true,
	initComponent : function() {
		this.selModel = Ext.create('Ext.selection.CheckboxModel');
		this.columns = [{
					xtype : 'rownumberer'
				}, {
					text : "图",
					dataIndex : "url",
					hidden : true,
					width : 60,
					renderer : function(v) {
						return "<img src='file/img/" + v
								+ "' width=50 height=50  />";
					},
					listeners : {
						"click" : function(a, b, row, cell, e, rowdata, g, e) {
							var win = Ext.create('Ext.window.Window', {
										title : "预览：" + rowdata.get("name"),
										width : 800,
										height : 500,
										layout : 'fit',
										modal : true,
										maximizable : true,
										// autoShow : true,
										html : "<img src='file/img/"
												+ rowdata.get("url") + "'/>"
									});
							win.show();
						}
					}
				}, {
					header : '文件名',
					dataIndex : 'name',
					flex : 1
				}, {
					header : '所属分类',
					dataIndex : 'typeText',
					flex : 1
				}, {
					header : '文件大小',
					dataIndex : 'size',
					flex : 1
				}, {
					header : 'URL',
					dataIndex : 'url',
					flex : 1
				}, {
					header : '状态',
					dataIndex : 'shzt',
					width : 60,
					renderer : function(v) {
						if (v == '0') {
							return '<font color="red" style="font-weight:bold">禁用</font>';
						} else if (v == '1') {
							return '<font color="green" style="font-weight:bold">通过</font>';
						} else {
							return '<font color="orange" style="font-weight:bold">待审</font>';
						}
					}
				}, {

					header : '来源',
					dataIndex : 'dataFrom',
					flex : 1
				}, {
					header : '上传用户',
					dataIndex : 'uploadUser',
					flex : 1
				}, {
					header : '上传时间',
					dataIndex : 'uploadTime',
					renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
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
		this.callOverridden(arguments);
		this.getStore().load();
	}
});