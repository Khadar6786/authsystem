# Google OAuth2 Setup Guide

This guide will help you configure Google OAuth2 authentication for your Spring Boot application.

## Prerequisites

- A Google account
- Access to Google Cloud Console (https://console.cloud.google.com)

## Step 1: Create a Google Cloud Project

1. Go to [Google Cloud Console](https://console.cloud.google.com)
2. Click on "Select a project" dropdown at the top
3. Click "NEW PROJECT"
4. Enter project name (e.g., "Auth System")
5. Click "CREATE"

## Step 2: Enable Google+ API

1. In your project dashboard, go to "APIs & Services" > "Library"
2. Search for "Google+ API"
3. Click on it and press "ENABLE"

## Step 3: Create OAuth2 Credentials

1. Go to "APIs & Services" > "Credentials"
2. Click "+ CREATE CREDENTIALS" at the top
3. Select "OAuth client ID"
4. If prompted, configure the OAuth consent screen:
   - Choose "External" for User Type
   - Fill in required fields:
     - App name: Auth System
     - User support email: Your email
     - Developer contact email: Your email
   - Click "SAVE AND CONTINUE"
   - Skip Scopes section (click "SAVE AND CONTINUE")
   - Skip Test users (click "SAVE AND CONTINUE")
5. Back to creating credentials:
   - Application type: **Web application**
   - Name: Auth System Web Client
   - Authorized redirect URIs: `http://localhost:8080/login/oauth2/code/google`
   - Click "CREATE"

## Step 4: Copy Credentials

After creating, you'll see a popup with:
- **Client ID**: A long string like `123456789-abc123def456.apps.googleusercontent.com`
- **Client Secret**: A random string

Copy both values!

## Step 5: Configure Application

### Option 1: Using application.properties (Development)

Open `src/main/resources/application.properties` and replace:

```properties
spring.security.oauth2.client.registration.google.client-id=your-client-id-here
spring.security.oauth2.client.registration.google.client-secret=your-client-secret-here
```

With your actual credentials from Step 4.

### Option 2: Using Environment Variables (Production - Recommended)

Set environment variables before running the application:

**Windows (PowerShell):**
```powershell
$env:GOOGLE_CLIENT_ID="your-client-id-here"
$env:GOOGLE_CLIENT_SECRET="your-client-secret-here"
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
export GOOGLE_CLIENT_ID="your-client-id-here"
export GOOGLE_CLIENT_SECRET="your-client-secret-here"
./mvnw spring-boot:run
```

## Step 6: Run the Application

1. Start the application:
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run
   
   # Linux/Mac
   ./mvnw spring-boot:run
   ```

2. Navigate to: http://localhost:8080/login

3. You should see two login options:
   - Traditional username/password form
   - **"Sign in with Google"** button

## Step 7: Test Google Login

1. Click "Sign in with Google"
2. Select your Google account
3. Grant permissions if prompted
4. You'll be redirected to the home page

## How It Works

### First-Time Google Login:
1. User clicks "Sign in with Google"
2. Redirected to Google for authentication
3. After successful auth, returns to app with user info
4. `CustomOAuth2UserService` checks if user exists:
   - **If new**: Creates account with email as username, random password, role USER
   - **If exists**: Updates OAuth2 provider info
5. User logged in and redirected to home page

### Subsequent Logins:
1. User clicks "Sign in with Google"
2. Google authenticates (may skip if already logged in)
3. User redirected to home page immediately

## Database Notes

### Development (H2):
- Default database is in-memory H2
- Data lost on application restart
- Access console at: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`

### Production (MySQL):
To use MySQL instead of H2:

1. Uncomment MySQL configuration in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/authsystem?useSSL=false&serverTimezone=UTC
   spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   ```

2. Comment out H2 configuration
3. Create database: `CREATE DATABASE authsystem;`
4. Restart application

## Security Best Practices

1. **Never commit credentials** to version control
2. Use environment variables in production
3. Use HTTPS in production (OAuth2 requires it)
4. Add your production domain to authorized redirect URIs:
   - `https://yourdomain.com/login/oauth2/code/google`

## Troubleshooting

### Error: "redirect_uri_mismatch"
- Ensure the redirect URI in Google Cloud Console exactly matches your app's URL
- Default: `http://localhost:8080/login/oauth2/code/google`

### Error: "Access blocked: This app's request is invalid"
- Verify Google+ API is enabled
- Check OAuth consent screen is configured
- Ensure app is not in testing mode (or add your email as test user)

### Login works but user not saved
- Check database connection
- Verify `spring.jpa.hibernate.ddl-auto=update` is set
- Check logs for errors

## Additional Resources

- [Spring Security OAuth2 Login](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html)
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [Google Cloud Console](https://console.cloud.google.com)
