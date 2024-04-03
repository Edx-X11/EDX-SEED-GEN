const express = require('express');
const bodyParser = require('body-parser');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware to parse JSON request bodies
app.use(bodyParser.json());

// Routes
app.get('/', (req, res) => {
    res.send('Welcome to the Seed Generator API!');
});

// Endpoint for generating seeds
app.post('/generate-seeds', (req, res) => {
    // Handle seed generation logic here
    // You can access request data from req.body
    // Generate seeds and save them to the database or file system
    // Respond with success or error message
    res.json({ message: 'Seeds generated successfully.' });
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
