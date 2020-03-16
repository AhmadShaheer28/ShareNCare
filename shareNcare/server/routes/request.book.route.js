const express = require('express');
const passport = require('passport');
const requestBookController = require('../controllers/request.book.controller');

const router = express.Router();


//router.use(passport.authenticate('jwt', { session: false }))
router.post('/addBookRequest', requestBookController.create);
router.post('/getBookRequest', requestBookController.getBookRequest);
router.post('/getMyRequest', requestBookController.getMyRequest);
router.delete('/deletebook', requestBookController.deletebook);;


module.exports = router;