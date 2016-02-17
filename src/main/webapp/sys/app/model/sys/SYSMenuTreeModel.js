Ext.define('EJ.model.sys.SYSMenuTreeModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'text', 'pid', 'icon', 'value', 'orderValue',
					'remark', 'viewType', {
						name : 'leaf',
						type : 'boolean'
					}, {
						name : 'expanded',
						type : 'boolean'
					}]
		});