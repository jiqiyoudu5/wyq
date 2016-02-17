Ext.define('EJ.model.sys.dictionary.SYSDicTreeModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'type', 'parent', 'text', 'icon', 'value',
					'expanded', 'leaf']
		});