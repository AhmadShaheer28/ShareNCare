const bcrypt = require('bcrypt');
const sql = require('../config/mysql');
const nodemailer = require('nodemailer');
const authCtrl = require('./auth.controller');
const apiCtrl = require('./api.controller.js');



const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'testingsoftaksolutions@gmail.com',
    pass: 'softak1234'
  }
});

module.exports = {
  create,
  update,
  list,
  get,
  deleteUser,
  updatePassword,
  forgetPasswordEmail,
  updateDescription,
  updateImage
}

function create(req, res) {
  // console.log(req.body);
  const email = req.body.email;
  const password = bcrypt.hashSync(req.body.password, 10);
  const name = req.body.name;
  const phone_no = req.body.phone_no;
  const gender = req.body.gender;
  const query = `insert into users (email, name, password, phone_no, gender) 
  values (?,?,?,?,?)`;
  const values = [email, name, password, phone_no, gender];
  sql.query({
    sql: query,
    values: values
  }, (err, data) => {
    if (err) return apiCtrl.apiError(res, err);
    // console.log(data);
    apiCtrl.apiSuccess(res, "User Created successfully!");

  });
}

function update(req, res) {
  // console.log(req.body);
  const username = req.body.userName;
  let password = null;
  const name = req.body.name;
  const role = req.body.role;
  let query = ``;
  if (req.body.password) {
    password = bcrypt.hashSync(req.body.password, 10);
    query = `update dim_categories set username='${username}', password='${password}', name='${name}', role='${role}' where id = ${id})`;
  } else {
    query = `update dim_categories set username='${username}', name='${name}', role='${role}' where id = ${id})`;
  }
  sql.query(query, (err, data) => {
    if (err) return apiCtrl.apiError(res, err);
    // console.log(data);
    apiCtrl.apiSuccess(res, "User Updated successfully!");
  });
}

function deleteUser(req, res) {
  let body = req.query;
  console.log(req.query);

  const query = `delete from users where id = ${body.id}`;
  sql.query(query, function (err, data) {
    if (err) return apiCtrl.apiError(res, err);
    apiCtrl.apiSuccess(res, "Successfully Deleted")
  })
}

function get(req, res) {
  let body = req.query;
  console.log(req.query);

  const query = `select * from users where id = ${body.id}`;
  sql.query(query, function (err, data) {
    if (err) return apiCtrl.apiError(res, err);
    delete data[0].password;
    apiCtrl.apiSuccess(res, { data: data[0] })
  })

}

function list(req, res) {
  let body = req.query,
    limit = body.limit ? body.limit : 50,
    offset = body.offset ? (body.offset * limit) : 0;

  const query = `select id, userName, name, role, created_at, updated_at from users order by id limit ${limit} offset ${offset}`;
  sql.query(query, function (err, data) {
    if (err) return apiCtrl.apiError(res, err);

    apiCtrl.apiSuccess(res, { data: data, count: data.length })
  })
}

function forgetPasswordEmail(req, res) {
  const query = `SELECT * FROM users WHERE userName='${req.body.userName}' or Email='${req.body.userName}' `;
  sql.query(query, function (err, data) {
    if (err) apiCtrl.apiError(res, err);
    console.log(data.recordset);
    if (data.recordset.length > 0) {
      let user = data.recordset[0];
      console.log(user);
      let token = authCtrl.generateToken(user);

      var mailOptions = {
        from: 'info@xyz.com',
        to: user.Email,
        subject: 'Reset your WMS password',
        html: `<h3>Hi ${user.FirstName}</h3>
<p>We received a request to reset your password for your UVS 7 account: ${user.UserName}</p><p>Please click on the link below to set a new password:</p><p><a href='http://uvs.codesorbit.com/reset-password/${user.UserName}/${token}' target="_blank">Reset your password</a></p><p>If you did not request to reset your password, you can safely ignore this email. Your password will not change.</p>`
      };

      transporter.sendMail(mailOptions, function (error, info) {
        if (error) apiCtrl.apiError(res, error);
        else apiCtrl.apiSuccess(res, user, 'Email Sent successfully!');
      });
    }
    else {
      apiCtrl.apiError(res, error, 'User Not found!');
    }

  });
}

function updatePassword(req, res) {
  const user = req.user,
    body = req.body,
    oldPassword = req.headers['oldpassword'],
    newPassword = req.headers['newpassword']
  bcrypt.compare(oldPassword, user.password, function (err, res) {
    console.log(res);
  });
  const query = `SELECT * FROM user WHERE ID='${user.ID}'`;
  sql.query(query, function (err, data) {
    if (err) console.log(err);
    let userData = data.recordset[0];
    if (!user || !bcrypt.compareSync(oldPassword, userData.Password)) {
      apiCtrl.apiError(res, '', 'Your current password could not be verified. Please try again.', 422);
      return;
      // return res.json({ error: 'Your login details could not be verified. Please try again.' });
    }
    insertPassword(user.ID, newPassword, res);
  });
}

function updateDescription(req, res) {
  let description = req.body.description;
  let query = `update users set description='${description}' where users.id=${req.user.id}`;
  sql.query(query, function (err, data) {
    if (err) return apiCtrl.apiError(res, err);

    apiCtrl.apiSuccess(res, "Description successfully updated!");
  })
}

function updateImage(req, res) {
  if (!req.files || !req.files.image) {
    apiCtrl.apiError(res, "Profile image is missing!");
  }

  let file = req.files.image;
  let fileName = file.name;
  fileName = fileName.replace(' ', '_');
  fileName = fileName.split('.');
  fileName = 'image_' + Date.now() + '.' + fileName.reverse()[0];

  file.mv(process.cwd() + '/server/assets/profile_images' + fileName, (err) => {
    if (err) {
      return apiCtrl.apiError(res, err);
    } else {
      let query = `update users set image_name='/user/images/${fileName}' where users.id=${req.user.id}`;
      sql.query(query, function (err, data) {
        if (err) return apiCtrl.apiError(res, err);
        apiCtrl.apiSuccess(res, { image_name: `/user/images/${fileName}` }, "Profile images successfully updated!");
      });
    }
  });

}