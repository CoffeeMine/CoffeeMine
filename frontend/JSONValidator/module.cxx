#include <rapidjson/document.h>
#include <rapidjson/rapidjson.h>
#include <rapidjson/schema.h>
#include <rapidjson/stringbuffer.h>
#include <rapidjson/writer.h>

#if 0
extern "C" int test(const char* json /*, const char* schema */) {
  rapidjson::Document d;
  d.Parse(json);

  rapidjson::StringBuffer buf;
  rapidjson::Writer<rapidjson::StringBuffer> w{buf};
  d.Accept(w);

  return buf.GetSize();
}
#endif

extern "C" int test(int v) noexcept { return v + 2; }