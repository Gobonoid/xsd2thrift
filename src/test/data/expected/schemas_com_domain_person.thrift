namespace * schemas.com.domain.person

include "schemas_com_domain_address.thrift"
include "schemas_com_domain_common.thrift"

struct personType
{
	1 : required string name,
	2 : required schemas_com_domain_common.country domicile,
	3 : required schemas_com_domain_address.addressType address,
}

