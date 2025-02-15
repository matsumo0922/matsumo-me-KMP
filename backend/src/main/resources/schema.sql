-- 1. ENUM型（記事のソースを定義）
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

-- 3. Qiita の記事詳細テーブル
CREATE TABLE qiita_article_detail
(
    id               SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_id        VARCHAR(50)  NOT NULL, -- Qiita 側の記事ID
    source_url       VARCHAR(255) NOT NULL, -- Qiita 記事の URL
    content          TEXT,                  -- Markdown 形式の本文（body）
    rendered_content TEXT,                  -- レンダリング後の本文（rendered_body）
    is_published     BOOLEAN      NOT NULL DEFAULT TRUE,
    comments_count   INTEGER,
    likes_count      INTEGER,
    reactions_count  INTEGER,
    stocks_count     INTEGER,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_qiita_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 4. Zenn の記事詳細テーブル
CREATE TABLE zenn_article_detail
(
    id                   SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_id        VARCHAR(50)  NOT NULL, -- Zenn 側の記事ID
    source_url       VARCHAR(255) NOT NULL, -- Zenn 記事の URL（取得または付与）
    content          TEXT,                  -- Markdown 形式の本文（body_markdown）
    rendered_content TEXT,                  -- レンダリング後の本文（Zenn では未提供の場合は NULL）
    is_published     BOOLEAN,               -- 公開状態（published）
    emoji            VARCHAR(10),           -- 絵文字
    article_type     VARCHAR(50),           -- 記事種別（例: "tech"）
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_zenn_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 5. Markdown（ローカル）記事詳細テーブル
CREATE TABLE markdown_article_detail
(
    id               SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_url       VARCHAR(255) NOT NULL, -- Markdown ファイルの URL（例: Git リポジトリ上の URL や CDN URL）
    content          TEXT,                  -- Markdown 形式の本文
    rendered_content TEXT,                  -- レンダリング後の本文（未加工の場合は NULL）
    is_published     BOOLEAN      NOT NULL DEFAULT TRUE,
    last_modified    TIMESTAMP,             -- ファイルの最終更新日時
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_markdown_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 6. Tag テーブル（共通タグ情報）
CREATE TABLE tag
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL UNIQUE,
    slug       VARCHAR(50),
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 7. ArticleTag 中間テーブル（記事とタグの多対多リレーション）
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
