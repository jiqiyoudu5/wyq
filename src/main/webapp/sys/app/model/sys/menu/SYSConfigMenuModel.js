Ext.define('EJ.model.sys.menu.SYSConfigMenuModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'text', 'pid', 'icon', 'value', 'orderValue',
					'remark', 'viewType', {
						name : 'leaf',
						type : 'boolean'
					}, {
						name : 'expanded',
						type : 'boolean'
					}],
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'menu/add',
					read : 'menu/read',
					update : 'menu/update',
					destroy : 'menu/delete'
				}
			}
		});