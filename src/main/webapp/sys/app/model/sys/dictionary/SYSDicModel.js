Ext.define('EJ.model.sys.dictionary.SYSDicModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'type', 'parent', 'text', 'remark', 'orderValue'],
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'dictionary/add',
					read : 'dictionary/read',
					update : 'dictionary/update',
					destroy : 'dictionary/delete'
				}
			}
		});