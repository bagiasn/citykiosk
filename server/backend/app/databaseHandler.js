var mongo = require('mongoskin');
var ObjectId = require('mongodb').ObjectID;
var db = mongo.db("mongodb://localhost:27017/cityKiosk", { native_parser: true });
db.bind('service');
db.bind('organization');

module.exports = {
	getListOfServices: function (callback) {
		db.service.find().toArray(function (err, serviceList) {
			callback(null, serviceList);
		});
	},
	getServiceById: function (id, callback) {
		db.service.find({ _id: ObjectId(id) }).toArray(function (err, service) {
			callback(null, service);
		});
	}
}
