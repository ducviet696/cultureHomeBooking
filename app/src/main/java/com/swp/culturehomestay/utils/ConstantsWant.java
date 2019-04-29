package com.swp.culturehomestay.utils;

import java.util.Locale;

public interface ConstantsWant {

	public static final String DEFAULT_LANGUAGE = "en";
	public static final String BUNDLE_NAME = "language.languageResource";
	public static final String CLIENT_ID_COM = "750931405059-n3uq6ogm6ncgj3o2t8fedv8g3fs62bfh.apps.googleusercontent.com";
	public static final String CLIENT_ID = "750931405059-n3uq6ogm6ncgj3o2t8fedv8g3fs62bfh";
	public static final String DATA_XML_PATH = "C:\\data\\ProjectData.xml";
	public static final String FOLDER_TEMP = "C:\\data\\temp\\student\\";
	public static final String FOLDER_REAL = "C:\\data\\real\\student\\";

	public interface Language {
		String ENGLISH_KEY = "en";
		String VIETNAM_KEY = "vi";
		public static final Locale DEFAULT_LOCALE = new Locale("en");
		public static final Locale ENGLISH_LOCALE = new Locale("en");
		public static final Locale VIETNAM_LOCALE = new Locale("vi");
		String LANGUAGE_SESSION = "lang";
	}

	public interface Message {
		public String Error = "error";
	}

	public interface ManagerPage {
		public String USER_PROFILE = "/Admin/StudenProfile";
	}

	public interface UserSessionDataKey {
		public String CODE_USER_ID = "codeUserId";
		public String USER_ID = "userId";
		public String FULL_NAME = "fullName";
		public String STUDENT_CODE = "studentCode";
		public String LANGUAGE_CODE = "languageCode";
		public String ROLE = "role";
		public String MANAGER_ROLE = "admin";
		public String STUDENT_ROLE = "student";
		public String PROFILE_GOOGLE = "profileGoogle";
	}

	public interface Login {
		public String GOOGLE_LOGIN_METHOD = "goo";
		public String PASSWORD_LOGIN_METHOD = "pass";
		public String FACEBOOK_LOGIN_METHOD = "fb";
	}
	public interface MessageWant{
		public interface Status{
			String SENT= "st";
			String RECEIVE = "rc";
			String SEEN ="se";
		}
	}

	public interface Transaction {
		public interface Reservation {
			public interface Status {
				String NOT_ACTIVE = "na";
				String ACTIVE = "ac";
				String HOST_CANCEL = "hcan";
				String USER_CANCEL = "Ucan";
				String SUCCESS = "su";
				String PENDDING = "pen";
				String ACCPET="acc";
			}
			public interface Purpose{
				String PARTY = "par";
				String FAMILY = "fa";
				String MEETING ="me";
				String OTHER  = "ot";
			}
			
		}

		public interface PaymentWant {
			public interface Status {
				String PAYED ="pay";
				String NOT_PAY="npay";
			}
			public interface Method{
				String PAYPAL ="pp";
				String CREDIT_CARD ="credit";
				String BAO_KIM ="bk";
				String WANT_WALLET="ww";
			}
			public interface Type{
				String TENANT_TO_WANT = "ttw";
				String WANT_TO_TENANT = "wtt";
				String HOST_TO_WANT = "htw";
				String WANT_TO_HOST = "wth";
				String TENANT_TO_TENANT ="ttt";
				String HOST_TO_HOST ="hhh";
			}
			public interface Purpose{
				String RESERVATION = "re";
				String TRANSFER_TO_WALLET ="tw";
				String TRANSFER_TO_ACCOUNT ="ta";
				String RETURN_DUETO_HOST="hca";
				String RETURN_DUETO_TENANT="tca";
				String WANT_PAY_WITHDRAW ="wpw";
			}
		}
		
		public interface PaymentMethod{
			public interface type{
				String PAYPAL ="pp";
				String WANT_PAYMENT ="ww";
			}
		}
	}

	public interface Homestay {
		public interface Type {
			public String APARTMENT = "ap";
			public String VILLA = "vla";
			public String STUDIO_APARTMENT = "sap";
			public String ENTIRE_HOUSE = "hou";
		}

		public interface RoomType {
			public String ENTIRE = "ent";
			public String SINGLE = "singe";
		}

		public interface Imagetype {
			String VERIFY = "veri";
			String GENERAL = "gene";
			String ENVIRONMEN = "envi";
			String BEDROOM = "bed";
			String LIVINGROOM = "livi";
			String BATHROOM = "bath";
			String KITCHEN = "kit";
		}

		public interface Currency {
			String VND = "vnd";
			String USD = "usd";
		}

		public interface BedType {
			String REAL_BED = "rb";
			String KING_SIZE = "ks";
			String TWIN_BED = "tb";
			String SOFA_BED = "sof";
			String SWING_BED = "swi";
		}

		public interface Policy {
			String FLEXIBLE = "flex";
			String MODERATE = "mod";
			String INSTANT = "ins";
			String REQUEST = "res";
		}

		public interface Amenity {
			String FAMILY = "fa";
			String KITCHEN = "kit";
			String ENTERTAIN = "enter";
			String ROOM_FACILITIES = "rf";
			String FACILITY = "fac";
			String SPECIAL = "spec";

		}

		public interface Status {
			String ACTIVE = "ac";
			String PENDDING = "pen";
			String REJECT = "rej";
			String NEW = "new";
			String CLOSE = "clo";

		}
	}

	public interface Restful {
		public interface FilterJson {
			public String USERWANT_FILTER = "userFilterJson";
			public String AUTH_ROLE_FILTER = "authRoleFilterJson";
			public String HOST_FILTER = "hostFilterJson";
			public String ADMIN_FILTER = "adminFilterJson";
			public String TENANT_FILTER = "tenantFilterJson";

			public String HOMESTAY_FILTER = "homestayFilter";
			public String HOMESTAYMULTI_FILTER = "homestayMultiFilter";
			public String ROOM_FILTER = "roomFilter";
			public String CULTURE_FILTER = "cultureServiceFilter";
			public String AMENITY_FILTER = "amenityFilter";
			public String ROOMIMANGE_FILTER = "roomImageFilter";
			public String HOMESTAYIMANGE_FILTER = "homestayImageFilter";
			public String PRICESPECIAL_FILTER = "priceSpecialFilter";
			public String HO_COL_FILTER = "homestay_cultureFilter";
			public String ADDRESS_FILTER = "addressFilter";
			public String CURRENTSTATUS_FILTER = "currentStatusFilter";
			public String FEEDBACK_FILTER = "feedBackFilter";

			public String AUTHORIZATIONMETHOD_FILTER = "authorizationMethodFilter";
			public String MESSAGE_FILTER = "messageFilter";
			public String WishList_FILTER = "wishListFilter";
			public String RESERVATION_FILTER = "reservationFilter";
			public String PAYMENTWANT_FILTER = "paymenWantFilter";
			String PAYMENTMETHOD_FILTER= "paymentMethodFilter";
			String REVSERVATION_EVENT_FILTER="reservationEventFilter";
		}

		public interface S3File {
			public String BUCKETS = "capstone-datafile";
			public String USER_FOLDER = "user";
			public String HOMESTAY_FOLDER = "homestay/house";
			public String ROOM_FOLDER = "homestay/room";
		}

		public interface AuthRole {
			public String ADMIN = "1";
			public String TENANT = "2";
			public String HOST = "3";
		}

		public interface Error {
			String HOMESTAY_NOT_FOUND = "04";
			String HOMESTAY_NOT_AVAILABLE = "06";
			String FEILD_REQUIRE_EMPTY = "11";
			String SUCCESS = "00";
			String ERROR_GENERAL = "01";
			String PAYPAL_NOT_PAY= "05";
			String BOOKED_HOMESTAY ="07";
			String  NOT_ENOUGH = "08";
		}
	}

	public interface JSF {
		public interface Session {
			String USERINFO = "userinfo";
		}
	}
}
