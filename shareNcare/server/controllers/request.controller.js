const sql = require('../config/mysql');
const apiCtrl = require('./api.controller.js');

module.exports = {
    create,
    list,
    restaurantList,
    joinRequest,
    getUsersByRequestId
}

function create(req, res) {
    console.log(req.body);
    const title = req.body.title;
    const description = req.body.description;
    const restaurant_id = req.body.restaurant_id;
    const fee = req.body.fee;
    const person_limit = req.body.person_limit;
    const meal_time = req.body.meal_time;
    const meal_date = req.body.meal_date;
    const last_join_date = req.body.last_join_date;
    const query = `insert into requests (title, description, user_id, restaurant_id, fee, person_limit, meal_time, meal_date, last_join_date) 
  values (?,?,?,?,?,?,?,?,?)`;
    const values = [title, description, req.user.id, restaurant_id, fee, person_limit, meal_time, meal_date, last_join_date];
    sql.query({
        sql: query,
        values: values
    }, (err, data) => {
        // console.log(err, data);
        if (err) return apiCtrl.apiError(res, "Error in creating request", err);
        apiCtrl.apiSuccess(res, "Request Created successfully!", "");

    });
}

function list(req, res) {
    let body = req.query;
    let limit = body.limit ? body.limit : 50,
        offset = body.offset ? (body.offset * limit) : 0,
        type = body.type;

    console.log(type);

    if (type == "APPLIED") {
        return appliedRequest(req, res);
    } else if(type == "CREATED"){
        return createdRequest(req, res);
    }

    const query = `select requests.*, users.name as user_name, restaurants.name as restaurant_name,
    if(concat(requests.meal_date, ' ', requests.meal_time) > NOW(), 0, 1) as expires, 
    requests.person_limit - (select count(*) from user_requests as ur where ur.request_id=requests.id) as space_left 
    from requests
    inner join users on requests.user_id=users.id
    inner join restaurants on restaurants.id=restaurant_id
    where requests.id not in (select user_requests.request_id from user_requests where user_requests.user_id=${req.user.id})
    and requests.user_id != ${req.user.id}
    order by created_at limit ${limit} offset ${offset}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function restaurantList(req, res) {
    let body = req.query,
        limit = body.limit ? body.limit : 50,
        offset = body.offset ? (body.offset * limit) : 0;

    const query = `select * from restaurants
    order by name limit ${limit} offset ${offset}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}

function joinRequest(req, res) {
    let request_id = req.body.request_id;

    const query = `insert into user_requests (user_id, request_id) values (${req.user.id}, ${request_id})`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, "User has successfully join th request!")
    })
}

function getUsersByRequestId(req, res) {
    let body = req.query,
        limit = body.limit ? body.limit : 50,
        offset = body.offset ? (body.offset * limit) : 0
    request_id = body.request_id;

    const query = `select users.* from user_requests 
    inner join users on users.id=user_requests.user_id
    where user_requests.request_id=${request_id}
    order by name limit ${limit} offset ${offset}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { users: data })
    })
}

function appliedRequest(req, res) {
    let body = req.query,
        limit = body.limit ? body.limit : 50,
        offset = body.offset ? (body.offset * limit) : 0;

    const query = `select requests.*, users.name as user_name, restaurants.name as restaurant_name,
    if(concat(requests.meal_date, ' ', requests.meal_time) > NOW(), 0, 1) as expires, 
    requests.person_limit - (select count(*) from user_requests as ur where ur.request_id=requests.id) as space_left
    from user_requests
    inner join requests on requests.id=user_requests.request_id
    inner join users on requests.user_id=users.id
    inner join restaurants on restaurants.id=restaurant_id
    where user_requests.user_id=${req.user.id} and requests.user_id != ${req.user.id}
    order by requests.title limit ${limit} offset ${offset}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}
function createdRequest(req, res) {
    let body = req.query,
        limit = body.limit ? body.limit : 50,
        offset = body.offset ? (body.offset * limit) : 0;

    const query = `select distinct requests.*, users.name as user_name, restaurants.name as restaurant_name,
    if(concat(requests.meal_date, ' ', requests.meal_time) > NOW(), 0, 1) as expires, 
    requests.person_limit - (select count(*) from user_requests as ur where ur.request_id=requests.id) as space_left
    from requests
    left join user_requests on requests.id=user_requests.request_id
    inner join users on requests.user_id=users.id
    inner join restaurants on restaurants.id=restaurant_id
    where requests.user_id=${req.user.id}
    order by requests.title limit ${limit} offset ${offset}`;
    sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);

        apiCtrl.apiSuccess(res, { data: data })
    })
}
