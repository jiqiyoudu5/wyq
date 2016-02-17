Ext.define('EJ.model.sys.resource.SYSResourceModel', {
			extend : 'Ext.data.Model',
			fields : ['id', 'url', 'name', 'endRegUser', {
						name : 'endUpdateTime',
						convert : timeConvertor
					}, 'remark'],
			proxy : {
				type : 'rest',
				actionMethods : {
					create : 'POST',
					read : 'GET',
					update : 'PUT',
					destroy : 'DELETE'
				},
				api : {
					create : 'resource/add',
					read : 'resource/read',
					update : 'resource/update',
					destroy : 'resource/delete'
				}
			}
		});