# Backend_sunJ

사이트 데이터를 스크래핑하고 원하는 데이터로 가공하여 JSON 형식으로 Response되도록 개발하였습니다.

회원가입 시 기준을 정해 유효성 검사 및 주민등록번호, 아이드 중복검사, 비밀번호 및 주민등록번호 암호화를 진행하였습니다.
비밀번호는 PasswordEncoder로 진행, 주민등록번호는 복호화가 가능해야 하므로 Base64로 인코더하였습니다.

로그인 시 아이디와 비밀번호 일치 여부 확인 후 일치하면 accessToken을 발행하였습니다

스크래핑 API와 데이터가공 API는 accessToken이 있는 경우만 접근이 가능합니다.
accessToken이 유효한 토큰인지 검사 후 API 진행하였고, Jsop 라이브러리를 이용하여 스크래핑 후 필요한 데이터는 데이터베이스에 저장하였습니다.
