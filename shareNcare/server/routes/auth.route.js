const express = require('express');
const passport = require('passport');
const userCtrl = require('../controllers/user.controller');
const authCtrl = require('../controllers/auth.controller');
const apiCtrl = require('../controllers/api.controller');
const router = express.Router();
module.exports = router;

router.post('/login', getUsernamePass, passport.authenticate('local', { session: false }), login);
router.post('/forget-password-email', userCtrl.forgetPasswordEmail);

function getUsernamePass(req, res, next) {
  req.body.email = req.headers['email'];
  req.body.password = req.headers['password'];
  next();
}

function login(req, res) {
  let user = req.user;
  let token = authCtrl.generateToken(user);
  apiCtrl.apiSuccess(res, { user, token });
}
