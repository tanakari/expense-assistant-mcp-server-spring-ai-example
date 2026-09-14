# expense-assistant-mcp-server-spring-ai-example

ローカルAIで育つ経費アシスタントを作る、ローカルAI向け経費アシスタントMCP Serverのサンプルです。

- プロジェクト / リポジトリ名: `expense-assistant-mcp-server-spring-ai-example`
- MCP Server名: `expense-assistant-mcp`

プロジェクト名ではSpring AIのMCP Serverサンプルであることを明示し、実行時のMCP Server名はTool利用側から扱いやすいよう短くしています。

## MCP Tool

- `get_expense_classification_rules`: 経費科目の判定ルールを取得する
- `fetch_expense_knowledge`: 登録済みの判定用ナレッジを作成日時の降順で全件取得する
- `register_expense_knowledge`: ユーザー確認によって得られた判定用ナレッジを登録する

将来的に `register_expense` を追加する想定ですが、現状は未作成です。

## 設計方針

- MCP ServerはTool単位ではなく「経費アシスタント」という業務単位でまとめる
- `classification/rule` は静的な判定基準を提供する
- `classification/knowledge` はユーザー確認によって得られた判定用ナレッジを取得・登録する
- ナレッジは判定ルールを上書きしない
- LLMの推測だけでナレッジを登録しない
- ナレッジ登録Toolは、ユーザーが登録内容を確認し、登録に同意した場合のみ呼び出す

## 構成

```text
src/main
├── java/com/example/expense/assistant/mcp
│   ├── ExpenseAssistantMcpServerSpringAiExampleApplication.java
│   └── classification
│       ├── rule
│       │   ├── ClassificationRulesResponse.java
│       │   ├── ClassificationRulesService.java
│       │   └── ClassificationRulesTool.java
│       └── knowledge
│           ├── ClassificationKnowledge.java
│           ├── ClassificationKnowledgeEntity.java
│           ├── ClassificationKnowledgeFetchResponse.java
│           ├── ClassificationKnowledgeRegistrationResponse.java
│           ├── ClassificationKnowledgeRepository.java
│           ├── ClassificationKnowledgeService.java
│           └── ClassificationKnowledgeTool.java
└── resources
    ├── application.yml
    └── classification
        └── classification-rules.md
```

## 起動

Java 21以降が必要です。プロジェクトのルートで実行します。

```bash
./mvnw spring-boot:run
```

Windows（PowerShell）の場合:

```powershell
.\mvnw.cmd spring-boot:run
```

MCP endpoint（Streamable HTTP）:

```text
http://localhost:8100/mcp
```

## ナレッジの保存

ナレッジはSpring Data JPA経由でH2に保存します。
現状は接続先を設定していないため、デフォルトのインメモリDBを使用し、アプリケーション終了時にデータは失われます。
再起動後も保持するには、ファイルDBへの接続設定と、テーブルを保持するスキーマ管理設定が必要です。
