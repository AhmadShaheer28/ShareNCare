const express = require('express');
const passport = require('passport');
const bookController = require('../controllers/book.controller');

const router = express.Router();


//router.use(passport.authenticate('jwt', { session: false }))
router.post('/addBook', bookController.create);
router.post('/getAllBooks', bookController.getAllBooks);
router.post('/getMyDonateBook', bookController.getMyDonateBook);
router.delete('/deletebook', bookController.deleteBook);;


module.exports = router;