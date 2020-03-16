const express = require('express');
const passport = require('passport');
const requestCtrl = require('../controllers/request.controller');

const router = express.Router();


router.use(passport.authenticate('jwt', { session: false }))
router.post('/create', requestCtrl.create);
router.post('/joinRequest', requestCtrl.joinRequest);
router.get('/restaurantList', requestCtrl.restaurantList);
router.get('/list', requestCtrl.list);
router.get('/getUsersByRequestId', requestCtrl.getUsersByRequestId);


module.exports = router;