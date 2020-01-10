#include <rapidjson/document.h>
#include <rapidjson/rapidjson.h>
#include <rapidjson/schema.h>

constexpr auto jschema = R"({
  "definitions":{},
  "$schema":"http://json-schema.org/draft-07/schema#",
  "$id":"http://example.com/root.json",
  "type":"object",
  "title":"The Root Schema",
  "required":[
    "name",
    "sprints",
    "tasks",
    "fragments"
  ],
  "properties":{
    "name":{
      "$id":"#/properties/name",
      "type":"string",
      "title":"The Name Schema",
      "default":"",
      "examples":[
        "alnum"
      ],
      "pattern":"^(.*)$"
    },
    "sprints":{
      "$id":"#/properties/sprints",
      "type":"array",
      "title":"The Sprints Schema",
      "items":{
        "$id":"#/properties/sprints/items",
        "type":"object",
        "title":"The Items Schema",
        "required":[
          "id",
          "start",
          "end",
          "tasks"
        ],
        "properties":{
          "id":{
            "$id":"#/properties/sprints/items/properties/id",
            "type":"number",
            "multipleOf":1.0,
            "title":"The Id Schema",
            "default":0,
            "examples":[
              0
          ]
          },
          "start":{
            "$id":"#/properties/sprints/items/properties/start",
            "type":"string",
            "title":"The Start Schema",
            "default":"",
            "examples":[
              "2011-12-03T10:15:30"
            ],
            "pattern":"^(.*)$"
          },
          "end":{
            "$id":"#/properties/sprints/items/properties/end",
            "type":"string",
            "title":"The End Schema",
            "default":"",
            "examples":[
              "2011-12-03T13:12:42"
            ],
            "pattern":"^(.*)$"
          },
          "meeting_id":{
            "$id":"#/properties/sprints/items/properties/meeting_id",
            "type":"number",
            "multipleOf":1.0,
            "title":"The Meeting_id Schema",
            "default":0,
            "examples":[
              2
            ]
          },
          "tasks":{
            "$id":"#/properties/sprints/items/properties/tasks",
            "type":"array",
            "title":"The Tasks Schema",
            "items":{
              "$id":"#/properties/sprints/items/properties/tasks/items",
              "type":"number",
              "multipleOf":1.0,
              "title":"The Items Schema",
              "default":0,
              "examples":[
                1,
                3,
                5,
                6,
                2
              ]
            }
          }
        }
      }
    },
    "tasks":{
      "$id":"#/properties/tasks",
      "type":"array",
      "title":"The Tasks Schema",
      "items":{
        "$id":"#/properties/tasks/items",
        "type":"object",
        "title":"The Items Schema",
        "required":[
          "id",
          "name",
          "hours",
          "description",
          "assignees",
          "fragments"
        ],
        "properties":{
          "id":{
            "$id":"#/properties/tasks/items/properties/id",
            "type":"number",
            "multipleOf":1.0,
            "title":"The Id Schema",
            "default":0,
            "examples":[
              0
            ]
          },
          "name":{
            "$id":"#/properties/tasks/items/properties/name",
            "type":"string",
            "title":"The Name Schema",
            "default":"",
            "examples":[
              "Do X"
            ],
            "pattern":"^(.*)$"
          },
          "hours":{
            "$id":"#/properties/tasks/items/properties/hours",
            "type":"number",
             "multipleOf":1.0,
             "title":"The Hours Schema",
             "default":0,
             "examples":[
               0
             ]
           },
          "description":{
            "$id":"#/properties/tasks/items/properties/description",
            "type":"string",
            "title":"The Description Schema",
            "default":"",
            "examples":[
              "X has Y; do Z with Y"
            ],
            "pattern":"^(.*)$"
          },
          "assignees":{
            "$id":"#/properties/tasks/items/properties/assignees",
            "type":"array",
            "title":"The Assignees Schema",
            "items":{
              "$id":"#/properties/tasks/items/properties/assignees/items",
              "type":"number",
              "multipleOf":1.0,
              "title":"The Items Schema",
              "default":0,
              "examples":[
                0,
                2,
                9
              ]
            }
          },
          "fragments":{
            "$id":"#/properties/tasks/items/properties/fragments",
            "type":"array",
            "title":"The Fragments Schema",
            "items":{
              "$id":"#/properties/tasks/items/properties/fragments/items",
              "type":"number",
              "multipleOf":1.0,
              "title":"The Items Schema",
              "default":0,
              "examples":[
                2,
                1,
                4
              ]
            }
          }
        }
      }
    },
    "fragments":{
      "$id":"#/properties/fragments",
      "type":"array",
      "title":"The Fragments Schema",
      "items":{
        "$id":"#/properties/fragments/items",
        "type":"object",
        "title":"The Items Schema",
        "required":[
          "id",
          "begin",
          "end",
          "users"
        ],
        "properties":{
          "id":{
            "$id":"#/properties/fragments/items/properties/id",
            "type":"number",
            "multipleOf":1.0,
            "title":"The Id Schema",
            "default":0,
            "examples":[
              5
            ]
          },
          "begin":{
            "$id":"#/properties/fragments/items/properties/begin",
            "type":"string",
            "title":"The Begin Schema",
            "default":"",
            "examples":[
              "2011-12-03T10:15:30"
            ],
            "pattern":"^(.*)$"
          },
          "end":{
            "$id":"#/properties/fragments/items/properties/end",
            "type":"string",
            "title":"The End Schema",
            "default":"",
            "examples":[
              "2011-12-03T13:12:42"
            ],
            "pattern":"^(.*)$"
          },
          "users":{
            "$id":"#/properties/fragments/items/properties/users",
            "type":"array",
            "title":"The Users Schema",
            "items":{
              "$id":"#/properties/fragments/items/properties/users/items",
              "type":"number",
              "multipleOf":1.0,
              "title":"The Items Schema",
              "default":0,
              "examples":[
                0,
                9,
                2
              ]
            }
          }
        }
      }
    }
  }
})";

extern "C" int test(char* json, int json_len, char* out /*, const char* schema */) {
  json[json_len] = '\0';
  rapidjson::Document d;
  rapidjson::ParseResult ok = d.ParseInsitu(json);
  if (!ok)
    return -1;

  rapidjson::Document sd;
  sd.Parse(jschema);
  rapidjson::SchemaDocument schema{sd};
  rapidjson::SchemaValidator validator{schema};
  if(!d.Accept(validator))
    return 1;
  return 0;
}