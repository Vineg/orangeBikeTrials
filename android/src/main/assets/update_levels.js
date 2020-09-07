'use strict';

const fs = require('fs');

const oldFolder = './levels-old/';
const folder = './levels/';

fs.readdirSync(folder).forEach(file => {
  let rawdata = fs.readFileSync(oldFolder + file);
  let data = JSON.parse(rawdata);
  fs.writeFileSync(
	folder + file, JSON.stringify(data, (k, v)=>(typeof(v)=="number" ? v * 1.8 : v))
  );
});
