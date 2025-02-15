-- 1. ENUM型（記事のソースを定義）
CREATE TYPE article_source AS ENUM ('markdown', 'qiita', 'zenn');

-- 2. Article テーブル（共通情報、タグ情報として tag_info[] を保持）
CREATE TABLE article
(
    id         SERIAL PRIMARY KEY,
    source_id  VARCHAR(50)    NOT NULL, -- 各記事ソースのID
    source     article_source NOT NULL,
    title      VARCHAR(255)   NOT NULL,
    summary    TEXT,
    tags       VARCHAR(50)[],
    created_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. Qiita の記事詳細テーブル（タグ情報を保持）
CREATE TABLE qiita_article_detail
(
    id               SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_id        VARCHAR(50)  NOT NULL, -- Qiita 側の記事ID
    source_url       VARCHAR(255) NOT NULL, -- Qiita 記事の URL
    title            VARCHAR(255) NOT NULL,
    content          TEXT,                  -- Markdown形式の本文（body）
    rendered_content TEXT,                  -- レンダリング後の本文（rendered_body）
    is_published     BOOLEAN      NOT NULL DEFAULT TRUE,
    comments_count   INTEGER,
    likes_count      INTEGER,
    reactions_count  INTEGER,
    stocks_count     INTEGER,
    tags             VARCHAR(50)[],
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_qiita_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 4. Zenn の記事詳細テーブル（タグ情報を保持）
CREATE TABLE zenn_article_detail
(
    id               SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_id        VARCHAR(50)  NOT NULL, -- Zenn 側の記事ID
    source_url       VARCHAR(255) NOT NULL, -- Zenn 記事の URL（取得または付与）
    title            VARCHAR(255) NOT NULL,
    content          TEXT,                  -- Markdown形式の本文（body_markdown）
    rendered_content TEXT,                  -- レンダリング後の本文（未提供の場合は NULL）
    is_published     BOOLEAN,               -- 公開状態（published）
    emoji            VARCHAR(10),           -- 絵文字
    article_type     VARCHAR(50),           -- 記事種別（例: "tech"）
    tags             VARCHAR(50)[],
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_zenn_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);

-- 5. Markdown（ローカル）記事詳細テーブル（タグ情報を保持）
CREATE TABLE markdown_article_detail
(
    id               SERIAL PRIMARY KEY,
    article_id       INTEGER      NOT NULL,
    source_id        VARCHAR(50)  NOT NULL, -- UUID
    source_url       VARCHAR(255) NOT NULL, -- MarkdownファイルのURL（例: Gitリポジトリ上やCDN上のURL）
    title            VARCHAR(255) NOT NULL,
    content          TEXT,                  -- Markdown形式の本文
    rendered_content TEXT,                  -- レンダリング後の本文（未加工の場合は NULL）
    is_published     BOOLEAN      NOT NULL DEFAULT TRUE,
    last_modified    TIMESTAMP,             -- ファイルの最終更新日時
    tags             VARCHAR(50)[],
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_markdown_article
        FOREIGN KEY (article_id) REFERENCES article (id)
);
