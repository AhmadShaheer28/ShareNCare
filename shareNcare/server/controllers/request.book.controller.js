const sql = require('../config/mysql');
const apiCtrl = require('./api.controller.js');

module.exports = {
    create,
    getBookRequest,
    getMyRequest,
    deletebook
}

function create(req, res) {
    console.log(req.body);
    const name = req.body.name;
    const owner_name = req.body.owner_name;
    const description = req.body.description;
    const address_owner = req.body.address_owner;
    const author = req.body.author;
    const status = req.body.status;
    const owner_id = req.body.owner_id;
    const quantity = req.body.quantity;
    const request_id  = req.body.request_id;
    const request_name  = req.body.request_name;
    const book_id  = req.body.book_id;
    const query = `insert into request_book (request_id, request_name, book_id, name, owner_name, description, address_owner, author, status, owner_id, quantity) 
  values (?,?,?,?,?,?,?,?,?,?,?)`;
    const values = [request_id, request_name, book_id, name, owner_name, description, address_owner, author, status, owner_id, quantity];
    sql.query({
        sql: query,
        values: values
    }, (err, data) => {
        // console.log(err, data);
        if (err) return apiCtrl.apiError(res, "Error in creating request", err);
        apiCtrl.apiSuccess(res, "Book Request Created successfully!", "");

    });
}

function getBookRequest(req, res) {
    const owner_id = req.body.owner_id;
    const query = `select * from request_book where owner_id = ${owner_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function getMyRequest(req, res) {
    const request_id = req.body.request_id;
    const query = `select * from request_book where request_id = ${request_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function deletebook(req, res) {
    const book_id = req.body.book_id;
    const query = `delete from request_book where book_id = ${book_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}
