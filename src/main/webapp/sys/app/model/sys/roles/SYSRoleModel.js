Ext.define('EJ.model.sys.roles.SYSRoleModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'code', 'name', 'remark'],
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'role/add',
					read : 'role/read',
					update : 'role/update',
					destroy : 'role/delete'
				}
			}
		});