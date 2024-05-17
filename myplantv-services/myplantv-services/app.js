const mongoose = require('mongoose');
const express = require('express');
const bodyParser = require('body-parser')

const Videos = connectMongoDB();
const app = express();

app.use(bodyParser.json());
app.listen(8080);

//This service returns all videos contained in the collection.
app.get('/videos', (req, res) => {
    let data
    Videos.find().then(result => {
        if (result) {
            if (result.length > 0) {
                data = { ok: true, result: result };
                res.send(data)
            }
            else {
                data = { ok: false, error: "No videos found" }
                res.send(data)
            }
        }
    })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error };
            res.send(data)
        })
});

//This service returns all videos of the given type, with the same structure than previous service.
app.get('/videos/type/:type', (req, res) => {
    let data
    Videos.find({ type: req.params.type }).then(result => {
        
        if (result) {
            if (result.length > 0) {
                data = { ok: true, result: result };
                res.send(data)
            }
            else {
                data = { ok: false, error: "No videos found" }
                res.send(data)
            }
        }

    })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error }
            res.send(data)
        })
});

//This service returns all videos of the given platform, with the same structure than previous service.
app.get('/videos/platform/:platform', (req, res) => {
    let data
    Videos.find({ platform: req.params.platform }).then(result => {
       
        if (result) {
            if (result.length > 0) {
                data = { ok: true, result: result };
                res.send(data);
            }
            else {
                data = { ok: false, error: "No videos found" }
                res.send(data);
            }
        }

    })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error };
            res.send(data);
        })
});

//This service returns all videos of the given category, with the same structure than previous service.
app.get('/videos/category/:category', (req, res) => {
    let data
    Videos.find({ category: req.params.category }).then(result => {
        
        if (result) {
            if (result.length > 0) {
                data = { ok: true, result: result };
                res.send(data);
            }
            else {
                data = { ok: false, error: "No videos found" }
                res.send(data);
            }
        }

    })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error };
            res.send(data);
        })
});

//This service returns all videos of the given minimum rating (included), sorted in descending order, 
//with the same structure than previous service.
app.get('/videos/rating/:rating', (req, res) => {
    const minRating = parseFloat(req.params.rating);
    let data

    if (isNaN(minRating)) {
        data = { ok: false, error: "The rating must be a valid number" };
        res.send(data);
        return;
    }
    else if (minRating < 0 || minRating > 5) {
        data = { ok: false, error: "The rating score must be between 0 and 5" };
        res.send(data);
        return;
    }

    Videos.find({ rating: { $gte: minRating } })
        .sort({ rating: -1 })
        .then(result => {
            if (result) {
                if (result.length > 0) {
                    data = { ok: true, result: result };
                    res.send(data);
                } else {
                    data = { ok: false, error: "No videos found" };
                    res.send(data);
                }
            }

        })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error };
            res.send(data);
        })
});

//This service returns all videos that can be watched considering current date, sorted by date in ascending order, 
//and excluding all the videos whose limit date (if any) has expired.
app.get('/videos/date', (req, res) => {
    const currentDate = new Date();
    let data

    Videos.find({ limitDate: { $gte: currentDate } })
        .sort({ limitDate: 1 })
        .then(result => {
            
            if (result.length > 0) {
                data = { ok: true, result: result };
                res.send(data);
            } else {
                data = { ok: false, error: "No limit date were found " + formatoDeseado };
                res.send(data);
            }
        })
        .catch(error => {
            data = { ok: false, error: "Error getting videos from the data base: " + error };
            res.send(data);
        })
})

//This service sends a new video to the server, with all the information needed.
//The service will return in the result attribute the inserted video with all its information, if everything goes as expected
app.post('/videos', (req, res) => {
    let newVideo = new Videos({
        title: req.body.title,
        type: req.body.type,
        platform: req.body.platform,
        category: req.body.category,
        rating: req.body.rating
    })
    let data
    newVideo.save().then(result => {
        data = { ok: true, result: result };
        res.send(data);
    })
        .catch(error => {
            data = { ok: false, error: "Error adding the video: " + error };
            res.send(data);
        })
});

//This service updates the contents of the video matching the id received. 
//The updated video must be included in the result of the response
app.put('/videos/:id', (req, res) => {
    let data
    Videos.findByIdAndUpdate(req.params.id,
        {
            title: req.body.title,
            type: req.body.type,
            platform: req.body.platform,
            category: req.body.category,
            rating: req.body.rating
        }, { new: true })
        .then(result => {
            data = { ok: true, result: result };
            res.send(data);
        })
        .catch(error => {
            data = { ok: false, error: "Error updating the video: " + error };
            res.send(data);
        })
});

//This service deletes the video matching the id received. 
//The deleted video must be included in the result of the response
app.delete('/videos/:id', (req, res) => {
    let data
    Videos.findByIdAndDelete(req.params.id)
        .then(result => {
            data = { ok: true, result: result };
            res.send(data);
        })
        .catch(error => {
            data = { ok: false, error: "Error deleting the video: " + error };
            res.send(data);
        })
});

function connectMongoDB() {
    mongoose.Promise = global.Promise;
    mongoose.connect('mongodb://127.0.0.1:27017/videos');

    const videoSchema = new mongoose.Schema({
        title: {
            required: true,
            type: String,
            minlength: 1,
        },
        type: {
            type: String,
            enum: ['movie', 'series', 'mini-series'],
        },
        platform: {
            required: true,
            type: String,
            enum: ['Netflix', 'HBO', 'Disney+', 'TV'],
        },
        category: {
            required: true,
            type: String,
            enum: ['comedy', 'thriller', 'adventures', 'other'],
        },
        rating: {
            required: true,
            type: Number,
            min: 0,
            max: 5,
        }
    });

    return mongoose.model('videos', videoSchema);
}