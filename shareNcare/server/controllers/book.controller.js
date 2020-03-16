const sql = require('../config/mysql');
const apiCtrl = require('./api.controller.js');

module.exports = {
    create,
    getAllBooks,
    getMyDonateBook,
    deleteBook
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
    const query = `insert into book (name, owner_name, description, address_owner, author, status, owner_id, quantity) 
  values (?,?,?,?,?,?,?,?)`;
    const values = [name, owner_name, description, address_owner, author, status, owner_id, quantity];
    sql.query({
        sql: query,
        values: values
    }, (err, data) => {
        // console.log(err, data);
        if (err) return apiCtrl.apiError(res, "Error in creating request", err);
        apiCtrl.apiSuccess(res, "Book Created successfully!", "");

    });
}

function getAllBooks(req, res) {
    let owner_id = req.body.owner_id;
    const query = `select * from book where owner_id != ${owner_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function getMyDonateBook(req, res) {
    const owner_id = req.body.owner_id;
    const query = `select * from book where owner_id = ${owner_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function deleteBook(req, res) {
    const book_id = req.body.book_id;
    const query = `delete from book where book_id = ${book_id}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}
