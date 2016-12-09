import Realm from 'realm';

class Pulse extends Realm.Object {}
Pulse.schema = {
  name: 'Pulse',
  primaryKey: 'id',
  properties: {
    id:    'int',    // primary key
    value: 'int',
    feeling: 'string',
    loggedAt: 'date'
  }
};

export default new Realm({schema: [Pulse]});
