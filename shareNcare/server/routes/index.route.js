const express = require('express');
const authRoutes = require('./auth.route');
const userRoutes = require("./user.route")
const requestRoutes = require("./request.route")
const bookRoutes = require("./book.route")
const requestBookRoutes = require("./request.book.route")
const fileUpload = require('express-fileupload');

const router = express.Router(); // eslint-disable-line new-cap
router.use(fileUpload());

/** GET /health-check - Check service health */
router.get('/health-check', (req, res) =>
    res.send('OK')
);
// router.use('/exports',express.static(process.cwd()+'/server/exports'));
// router.use('/firmwareDownload',express.static(process.cwd()+'/server/assests/Firmwares'));
router.use('/auth', authRoutes);
router.use('/user', userRoutes);
router.use('/request', requestRoutes);
router.use('/book', bookRoutes);
router.use('/requestBook', requestBookRoutes);
module.exports = router;
