#include <rapidjson/document.h>
#include <rapidjson/rapidjson.h>
//#include <rapidjson/schema.h>
//#include <rapidjson/stringbuffer.h>
//#include <rapidjson/writer.h>
#include <rapidjson/error/en.h>
#include <cstring>
#include <cstdio>

extern "C" int test(char* json, int json_len, char* out /*, const char* schema */) {
  json[json_len] = '\0';
  rapidjson::Document d;
  rapidjson::ParseResult ok = d.ParseInsitu(json);
  if (!ok){
    return 5;
      //return std::sprintf(json, "JSON parse error: %s (%zu)\n", rapidjson::GetParseError_En(ok.Code()), ok.Offset());
  }

  /*
  rapidjson::StringBuffer buf;
  rapidjson::Writer<rapidjson::StringBuffer> w{buf};
  d.Accept(w);
  return buf.GetSize();
  */
   return 0;
}