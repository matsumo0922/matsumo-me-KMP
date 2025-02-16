# matsumo-me-KMP

## Development

1. `brew instal supabase/tap/supabase`
2. run docker
3. `supabase start`
4. run `/backend/src/resources/schema.sql` in the SQL editor
5. create `local.properties` in the root directory and add `SUPABASE_URL` and `SUPABASE_KEY` to it
6. `./gradlew :backend:run`
7. `./gradlew :frontend:wasmJsBrowserDevelopmentRun`
