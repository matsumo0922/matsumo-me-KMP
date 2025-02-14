-- 1. ENUM型を作成（記事のソースを定義）
CREATE TYPE article_source AS ENUM ('markdown', 'qiita', 'zenn');

-- 2. Article テーブル（共通情報）
CREATE TABLE article
(
    id           SERIAL PRIMARY KEY,
    source       article_source NOT NULL,
    title        VARCHAR(255)   NOT NULL,
    summary      TEXT,
    published_at TIMESTAMP,
    created_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. QiitaArticleDetail テーブル（Qiita 固有の詳細情報）
CREATE TABLE qiita_article_detail
(
    id              SERIAL PRIMARY KEY,
    article_id      INTEGER     NOT NULL,
    external_id     VARCHAR(50) NOT NULL, -- Qiita側の記事ID（例："2cfc1123694c0e8dc39e"）
    body            TEXT,                 -- Markdown形式の本文
    rendered_body   TEXT,                 -- レンダリング後の本文
    coediting       BOOLEAN,
    comments_count  INTEGER,
    likes_count     INTEGER,
    reactions_count INTEGER,
    stocks_count    INTEGER,
    user_info       JSON,                 -- Qiitaユーザー情報（必要な項目を含む）
    created_at      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_qiita_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 4. ZennArticleDetail テーブル（Zenn 固有の詳細情報）
CREATE TABLE zenn_article_detail
(
    id                   SERIAL PRIMARY KEY,
    article_id           INTEGER     NOT NULL,
    external_id          VARCHAR(50) NOT NULL, -- Zenn側の記事ID（例："229820"）
    body_markdown        TEXT,                 -- Zenn の記事本文（Markdown形式）
    published            BOOLEAN,
    scheduled_publish_at TIMESTAMP,
    emoji                VARCHAR(10),
    article_type         VARCHAR(50),
    github_repository_id VARCHAR(50),
    topics               JSON,                 -- トピック情報（例：各 topic の id, name, image_url, display_name など）
    publication          JSON,                 -- 出版物情報（必要に応じて）
    created_at           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_zenn_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 5. MarkdownArticleDetail テーブル（Markdown 方式＝ローカルファイルの場合）
--    ※ file_path を url に変更（例えば、Gitリポジトリ内のURLや、CDN上のURL等）
CREATE TABLE markdown_article_detail
(
    id            SERIAL PRIMARY KEY,
    article_id    INTEGER      NOT NULL,
    url           VARCHAR(255) NOT NULL,
    content       TEXT, -- Markdown形式の本文
    last_modified TIMESTAMP,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_markdown_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 6. Tag テーブル
CREATE TABLE tag
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,
    slug       VARCHAR(50),
    extra_data JSON, -- 必要に応じて（例：Zenn の topics の image_url など）
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 7. ArticleTag（中間テーブル：記事とタグの多対多リレーション）
CREATE TABLE article_tag
(
    article_id INTEGER   NOT NULL,
    tag_id     INTEGER   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (article_id, tag_id),
    CONSTRAINT fk_article
        FOREIGN KEY (article_id) REFERENCES article (id),
    CONSTRAINT fk_tag
        FOREIGN KEY (tag_id) REFERENCES tag (id)
);
