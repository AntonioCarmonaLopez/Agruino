//require new install module firebase-admin
const admin = require('firebase-admin');
//file that contains our auth token for firebase connect
let serviceAccount = require('./agruino-firebase-adminsdk-b0dvg-547f805181.json');
//start database
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
//cath database
let db = admin.firestore();

let values=fetchPrevious();
someFunction();


function delay(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function someFunction() {
 await delay(5000);
 await console.log(values);
}
//function for set new data
function uploadData(moisture, conductivity, turbidity, ph, temp) {
  //variables for stored previous values
  let moisPrev, conPrev, turPrev, phPrev, tempPrev;
db.collection('values').doc('YC0HJwj1qynXC12LVpAV').update({
  moisture: parseFloat(moisture),
  conductivity: parseFloat(conductivity),
  turbidity: parseFloat(turbidity),
  ph: parseFloat(ph),
  temp: parseFloat(temp),
});

moisPrev = values[0];
conPrev = values[1];
turPrev = values[2];
phPrev = values[3];
tempPrev = values[4];
//uploadDataHistory(moisture,moisPrev,conductivity,conPrev,turbidity,turPrev,ph,phPrev,temp,tempPrev);
}
function fetchPrevious() {
  var myArray = new Array(4);
  //variables for stored previous values
  let moisPrev, conPrev, turPrev, phPrev, tempPrev;
  let values = [];
let values_ = db.collection('values').doc('YC0HJwj1qynXC12LVpAV');
let getDoc = values_.get()
  .then(doc => {
    if (!doc.exists) {
      console.log('No such document!');
    } else {
      values[0] = doc.data().moisture;
      values[1] = doc.data().conductivity;
      values[2] = doc.data().turbidity;
      values[3] = doc.data().temp;
      values[4] = doc.data().ph;
      console.log('Document data:', values);
      return values;
    }
  })
  .catch(err => {
    console.log('Error getting document', err);
  });
  return myArray;
}

function fetch() {
  let values_history = db.collection('values_history').doc('59DXbz5zbanZjBRhkTqv');
  let getDoc = values_history.get()
    .then(doc => {
      if (!doc.exists) {
        console.log('No such document!');
      } else {
        console.log('Document data:', doc.data());
        console.log(doc.data().ph)
      }
    })
    .catch(err => {
      console.log('Error getting document', err);
    });

}
