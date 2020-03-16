const express = require('express');
const passport = require('passport');
const userCtrl = require('../controllers/user.controller');

const router = express.Router();

router.post('/create', userCtrl.create);
router.use('/images',express.static(process.cwd()+'/server/assets/profile_images'));



router.use(passport.authenticate('jwt', { session: false }))

router.post('/update-password', userCtrl.updatePassword);
router.get('/list/:limit?/:offset?', userCtrl.list);
router.get('/get', userCtrl.get);
router.post('/update', userCtrl.update);
router.post('/updateDescription', userCtrl.updateDescription);
router.post('/updateImage', userCtrl.updateImage);
router.delete('/delete', userCtrl.deleteUser);

module.exports = router;